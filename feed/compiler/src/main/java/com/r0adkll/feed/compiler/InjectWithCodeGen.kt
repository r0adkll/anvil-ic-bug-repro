package com.r0adkll.feed.compiler

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.r0adkll.common.FeatureInjector
import com.r0adkll.common.InjectWith
import com.r0adkll.common.ScopedComponentHolder
import com.r0adkll.common.scopes.FeatureScope
import com.r0adkll.common.scopes.SingleIn
import com.r0adkll.common.scopes.UserScope
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.internal.createAnvilSpec
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.kspDependencies
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Inject

object InjectWithCodeGen {

  class KspGenerator(
    val env: SymbolProcessorEnvironment
  ) : SymbolProcessor {

    @AutoService(SymbolProcessorProvider::class)
    class Provider : SymbolProcessorProvider {
      override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        environment.logger.info("Loading provider")
        return KspGenerator(environment)
      }
    }

    @OptIn(ExperimentalAnvilApi::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
      resolver.getSymbolsWithAnnotation(InjectWith::class.qualifiedName!!)
        .filterIsInstance<KSClassDeclaration>()
        .forEach { clazz ->
          env.logger.info("Generating for ${clazz.qualifiedName?.asString()}")

          val fileSpec = FileSpec.createAnvilSpec(
            packageName = clazz.packageName.asString(),
            fileName = "${clazz.simpleName.asString()}_Generated",
          ) {
            addFeatureComponent(clazz)
            addFeatureInjector(clazz)
            addFeatureInjectorBinder(clazz)
          }

          fileSpec.writeTo(
            codeGenerator = env.codeGenerator,
            dependencies = fileSpec.kspDependencies(aggregating = false),
          )
        }

      return emptyList()
    }
  }

  private fun FileSpec.Builder.addFeatureComponent(clazz: KSClassDeclaration) {
    val componentName = ClassName(clazz.packageName.asString(), "${clazz.simpleName.asString()}Component")

    // @SingleIn(FeatureScope)
    addType(
      TypeSpec.interfaceBuilder(componentName).apply {
        addAnnotation(
          AnnotationSpec.builder(SingleIn::class)
            .addMember("%T::class", FeatureScope::class.asClassName())
            .build(),
        )


        // @ContributesSubcomponent(
        //   scope = FeatureScope::class,
        //   parentScope = UserScope::class,
        // )
        addAnnotation(
          AnnotationSpec.builder(ContributesSubcomponent::class)
            .addMember("scope = %T::class", FeatureScope::class.asClassName())
            .addMember("parentScope = %T::class", UserScope::class.asClassName())
            .build()
        )

        // fun inject(target: SomeAnnotatedClass)
        addFunction(
          FunSpec.builder("inject")
            .addModifiers(KModifier.ABSTRACT)
            .addParameter("target", clazz.toClassName())
            .build(),
        )

        // Comment this out to re-produce the Anvil generated ParentComponent on contributed
        // subcomponent implementations
        addType(
          TypeSpec.interfaceBuilder("Parent")
            .addAnnotation(
              AnnotationSpec.builder(ContributesTo::class)
                .addMember("%T::class", UserScope::class.asClassName())
                .build(),
            )
            .addFunction(
              FunSpec.builder(clazz.componentFactoryProvisionName())
                .addModifiers(KModifier.ABSTRACT)
                .returns(componentName.nestedClass("Factory"))
                .build(),
            )
            .build(),
        )

        addType(
          TypeSpec.interfaceBuilder("Factory")
            .addAnnotation(ContributesSubcomponent.Factory::class.asClassName())
            .addFunction(
              FunSpec.builder("create")
                .addModifiers(KModifier.ABSTRACT)
                .apply {
                  addParameter(
                    ParameterSpec.builder("target", clazz.toClassName())
                      .addAnnotation(BindsInstance::class.asClassName())
                      .build(),
                  )
                }
                .returns(componentName)
                .build(),
            )
            .build(),
        )

      }.build()
    )
  }

  private const val COMPONENT_FACTORY_FIELD_NAME = "componentFactory"
  private const val PARAM_TARGET = "target"
  private const val PARAM_FACTORY = "factory"
  internal const val PARAM_SCREEN = "screen"
  private const val VAL_DEPENDENCIES = "dependencies"
  private const val VAL_COMPONENT = "component"

  private val FeatureInjectorClassName = FeatureInjector::class.asClassName()
  private val ScopedComponentHolderClassName = ScopedComponentHolder::class.asClassName()

  private fun FileSpec.Builder.addFeatureInjector(clazz: KSClassDeclaration) {
    val packageName = clazz.packageName.asString()
    val simpleName = clazz.simpleName.asString()
    val componentName = ClassName(packageName, "${simpleName}Component")
    val componentFactoryName = componentName.nestedClass("Factory")
    val featureInjectorClassName = ClassName(packageName, "${simpleName}FeatureInjector")

    addType(
      TypeSpec.classBuilder(featureInjectorClassName).apply {
        // Setup constructor
        primaryConstructor(
          FunSpec.constructorBuilder()
            .addAnnotation(Inject::class.asClassName())
            .addParameter(COMPONENT_FACTORY_FIELD_NAME, componentFactoryName)
            .build(),
        )
        addProperty(
          PropertySpec.builder(COMPONENT_FACTORY_FIELD_NAME, componentFactoryName)
            .initializer(COMPONENT_FACTORY_FIELD_NAME)
            .addModifiers(KModifier.PRIVATE)
            .build(),
        )

        // Set FeatureInjector supertype
        val featureInjector = FeatureInjectorClassName.parameterizedBy(
          clazz.toClassName(),
          Unit::class.asClassName(),
        )
        addSuperinterface(featureInjector)

        // Add inject function
        val factoryLambdaType = LambdaTypeName.get(
          returnType = Unit::class.asClassName(),
        )
        addFunction(
          FunSpec.builder("inject")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(PARAM_TARGET, clazz.toClassName())
            .addParameter(PARAM_FACTORY, factoryLambdaType)
            .addCode(
              buildCodeBlock {
                // Build component
                addStatement("val %L = %L.create(", VAL_COMPONENT, COMPONENT_FACTORY_FIELD_NAME)
                indent()
                addStatement("%L = %L,", PARAM_TARGET, PARAM_TARGET)
                unindent()
                addStatement(")")

                // Inject target
                addStatement("%L.inject(%L)", VAL_COMPONENT, PARAM_TARGET)

                // Return new ScopedComponentHolder
                addStatement("return ScopedComponentHolder(%L)", VAL_COMPONENT)
              },
            )
            .returns(ScopedComponentHolderClassName)
            .build(),
        )
      }.build(),
    )
  }

  private fun FileSpec.Builder.addFeatureInjectorBinder(clazz: KSClassDeclaration) {
    val packageName = clazz.packageName.asString()
    val simpleName = clazz.simpleName.asString()
    val featureInjectorClassName = ClassName(packageName, "${simpleName}FeatureInjector")
    val featureInjectorBinderClassName = ClassName(packageName, "${simpleName}FeatureInjectorBinder")
    addType(
      TypeSpec.interfaceBuilder(featureInjectorBinderClassName)
        .addAnnotation(Module::class.asClassName())
        .addAnnotation(
          AnnotationSpec.builder(ContributesTo::class.asClassName())
            .addMember("%T::class", UserScope::class.asClassName())
            .build(),
        )
        .addFunction(
          FunSpec.builder("bind${featureInjectorClassName.simpleName}")
            .addAnnotation(IntoMap::class.asClassName())
            .addAnnotation(Binds::class.asClassName())
            .addAnnotation(
              AnnotationSpec.builder(ClassKey::class.asClassName())
                .addMember("%T::class", clazz.toClassName())
                .build(),
            )
            .addModifiers(KModifier.ABSTRACT)
            .addParameter("injector", featureInjectorClassName)
            .returns(FeatureInjectorClassName.parameterizedBy(STAR, STAR))
            .build(),
        )
        .build(),
    )
  }

  private fun KSClassDeclaration.componentFactoryProvisionName(): String {
    /*
     * Sometimes, two classes named the same can be annotated with @InjectWith and cause
     * a collision of this name as its contributed to the same interface/Component. So to prevent
     * collisions each factory accessor method name will be fully qualified
     */
    return "${packageName.asString().replace(".", "_")}_${simpleName.asString()}ComponentFactory"
  }
}