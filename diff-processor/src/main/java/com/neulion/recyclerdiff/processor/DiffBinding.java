package com.neulion.recyclerdiff.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;
import java.util.TreeSet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;


/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-15
 * Time: 11:26
 */
class DiffBinding
{
    private ClassName mInterfaceClassName = ClassName.get("com.neulion.core.widget.recyclerview.diff", "IDiffComparable");

    private ClassName mMapClassName = ClassName.get("java.util", "Map");

    private ClassName mHashMapClassName = ClassName.get("java.util", "HashMap");

    private ClassName mParamString = ClassName.get("java.lang", "String");

    private ClassName mParamObject = ClassName.get("java.lang", "Object");

    private TypeName mParameterStringObjectType = ParameterizedTypeName.get(mMapClassName, mParamString, mParamObject);

    private TypeName mParameterStringObjectHashMapType = ParameterizedTypeName.get(mHashMapClassName, mParamString, mParamObject);

    private ClassName mBindingClassName;

    private TypeName mTypeName;

    private Set<String> mItemFiledSet;

    private Set<String> mItemMethodSet;

    private Set<String> mContentFiledSet;

    private Set<String> mContentMethodSet;

    DiffBinding(TypeElement typeElement, Set<? extends Element> itemSet, Set<? extends Element> contentSet)
    {
        String qualifiedName = typeElement.getQualifiedName().toString();

        String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));

        String className = typeElement.getSimpleName().toString();

        mBindingClassName = ClassName.get(packageName, className + "_DiffBinding");

        mTypeName = TypeName.get(typeElement.asType());

        mItemFiledSet = new TreeSet<>();

        mItemMethodSet = new TreeSet<>();

        mContentFiledSet = new TreeSet<>();

        mContentMethodSet = new TreeSet<>();

        // item
        for (Element element : itemSet)
        {
            if (element.getKind() == ElementKind.FIELD)
            {
                VariableElement variableElement = (VariableElement) element;

                mItemFiledSet.add(variableElement.getSimpleName().toString());
            }
            else if (element.getKind() == ElementKind.METHOD)
            {
                ExecutableElement executableElement = (ExecutableElement) element;

                mItemMethodSet.add(executableElement.getSimpleName().toString());
            }
        }

        for (Element element : contentSet)
        {
            if (element.getKind() == ElementKind.FIELD)
            {
                VariableElement variableElement = (VariableElement) element;

                mContentFiledSet.add(variableElement.getSimpleName().toString());
            }
            else if (element.getKind() == ElementKind.METHOD)
            {
                ExecutableElement executableElement = (ExecutableElement) element;

                mContentMethodSet.add(executableElement.getSimpleName().toString());
            }
        }
    }

    // --------------------------------------------------------------------------------------
    // generate java code
    // --------------------------------------------------------------------------------------
    JavaFile preJavaFile()
    {
        return JavaFile.builder(mBindingClassName.packageName(), createClass())

                .addFileComment("Generated code from annotation processor. Do not modify!!!")

                .build();
    }

    private TypeSpec createClass()
    {
        TypeSpec.Builder result = TypeSpec.classBuilder(mBindingClassName.simpleName()).addModifiers(Modifier.PUBLIC);

        // interface
        result.addSuperinterface(ParameterizedTypeName.get(mInterfaceClassName, mBindingClassName));

        // field
        // mSource
        result.addField(mTypeName, "mSource");

        // Map<String,String>
        result.addField(mParameterStringObjectType, "mItems");

        // Map<String,String>
        result.addField(mParameterStringObjectType, "mContents");

        result.addMethod(createMap("getItems", mItemFiledSet, mItemMethodSet));

        result.addMethod(createMap("getContents", mContentFiledSet, mContentMethodSet));

        result.addMethod(addInterfaceItemsMethod());

        result.addMethod(addInterfaceContentsMethod());

        result.addMethod(addInterfacePayloadMethod());

        // constructor
        result.addMethod(createConstructor(mTypeName));

        return result.build();
    }

    private MethodSpec createConstructor(TypeName typeName)
    {
        MethodSpec.Builder constructor = MethodSpec.constructorBuilder()

                .addModifiers(Modifier.PUBLIC)

                .addParameter(typeName, "source", Modifier.FINAL)

                .addStatement("this.$N=$N", "mSource", "source")

                .addStatement("this.$N=$L()", "mItems", "getItems")

                .addStatement("this.$N=$L()", "mContents", "getContents");

        return constructor.build();
    }

    private MethodSpec createMap(String methodName, Set<String> fieldSet, Set<String> methodSet)
    {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName);

        builder.addModifiers(Modifier.PUBLIC);

        builder.returns(mParameterStringObjectType);

        builder.addStatement("$T result=new $T()", mParameterStringObjectType, mParameterStringObjectHashMapType);

        for (String key : methodSet)
        {
            builder.addStatement("result.put($S,mSource.$L())", key, key);
        }

        for (String key : fieldSet)
        {
            builder.addStatement("result.put($S,mSource.$L)", key, key);
        }

        builder.addStatement("return result");

        return builder.build();
    }

    // -----------------------------------------------------------------------------------------
    // IDiffComparable<T>
    // -----------------------------------------------------------------------------------------
    private MethodSpec addInterfaceItemsMethod()
    {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("compareObject");

        builder.addAnnotation(Override.class);

        builder.addModifiers(Modifier.PUBLIC);

        builder.returns(TypeName.BOOLEAN);

        builder.addParameter(mBindingClassName, "t", Modifier.FINAL);

        builder.addStatement("return $T.compare($L,$L)", ClassName.get("com.neulion.core.util", "MapDiffUtil"), "mItems", "t.mItems");

        return builder.build();
    }

    private MethodSpec addInterfaceContentsMethod()
    {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("compareContent");

        builder.addAnnotation(Override.class);

        builder.addModifiers(Modifier.PUBLIC);

        builder.returns(TypeName.BOOLEAN);

        builder.addParameter(mBindingClassName, "t", Modifier.FINAL);

        builder.addStatement("return $T.compare($L,$L)", ClassName.get("com.neulion.core.util", "MapDiffUtil"), "mContents", "t.mContents");

        return builder.build();
    }

    private MethodSpec addInterfacePayloadMethod()
    {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getChangePayload");

        builder.addAnnotation(Override.class);

        builder.addModifiers(Modifier.PUBLIC);

        builder.returns(TypeName.OBJECT);

        builder.addParameter(mBindingClassName, "t", Modifier.FINAL);

        builder.addStatement("return $T.diff($L,$L)", ClassName.get("com.neulion.core.util", "MapDiffUtil"), "mContents", "t.mContents");

        return builder.build();
    }
}
