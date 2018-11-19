package com.neulion.recyclerdiff.processor;

import com.google.auto.service.AutoService;
import com.neulion.recyclerdiff.annotation.DiffContent;
import com.neulion.recyclerdiff.annotation.DiffItem;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

/**
 * User: NeuLion
 */
@SuppressWarnings("unused")
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DiffProcessor extends AbstractProcessor
{
    private Messager mMessager;

    private Filer mFileCreator;

    private Map<TypeElement, DiffBinding> mBindClassMap = new LinkedHashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        Set<String> annotations = new HashSet<>();

        annotations.add(DiffItem.class.getName());

        annotations.add(DiffContent.class.getName());

        return annotations;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment)
    {
        super.init(processingEnvironment);

        mMessager = processingEnv.getMessager();

        mFileCreator = processingEnv.getFiler();

        mBindClassMap.clear();

        mMessager.printMessage(Kind.NOTE, getClass().getSimpleName() + " init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        mMessager.printMessage(Kind.NOTE, getClass().getSimpleName() + " process");

        if (mBindClassMap.size() == 0)
        {
            Set<Element> classSet = getClassSet(annotations, roundEnv);

            for (Element element : classSet)
            {
                TypeElement enclosingElement = (TypeElement) element;

                DiffBinding bindClass = mBindClassMap.get(enclosingElement);

                if (bindClass == null)
                {
                    mBindClassMap.put(enclosingElement, new DiffBinding(enclosingElement,

                            getElementsAnnotationWith(roundEnv, DiffItem.class, enclosingElement),

                            getElementsAnnotationWith(roundEnv, DiffContent.class, enclosingElement)));
                }
            }

            if (mBindClassMap.size() > 0)
            {
                for (TypeElement key : mBindClassMap.keySet())
                {
                    try
                    {
                        mBindClassMap.get(key).preJavaFile().writeTo(mFileCreator);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    /**
     * find all class with Annotation(DiffItem,DiffContent)
     *
     * @see DiffItem
     * @see DiffContent
     */
    private Set<Element> getClassSet(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        Set<Element> result = new HashSet<>();

        for (TypeElement annotationType : annotations)
        {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotationType))
            {
                result.add(element.getEnclosingElement());
            }
        }

        return result;
    }

    /**
     * find all method or filed item with Annotation in special class
     */
    private Set<Element> getElementsAnnotationWith(RoundEnvironment roundEnv, Class<? extends Annotation> annotation, TypeElement typeElement)
    {
        Set<Element> result = new HashSet<>();

        for (Element element : roundEnv.getElementsAnnotatedWith(annotation))
        {
            if (element.getEnclosingElement().equals(typeElement))
            {
                result.add(element);
            }
        }

        return result;
    }
}
