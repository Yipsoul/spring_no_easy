package cn.it.yip.core.io;

import cn.it.yip.utils.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-27 14:57
 **/
public class ClassResourcesLoader {
    private final ClassLoader classLoader;

    public ClassResourcesLoader() {
        this.classLoader = getClassLoader();
    }

    public ClassLoader getClassLoader() {
        return ClassUtils.getDefaultClassLoader();
    }

    public ClassResourcesLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Resource[] getResources(String classPath) {
        return converterClassPath(classPath, this.classLoader);
    }

    protected Resource[] converterClassPath(String classPath, ClassLoader classLoader) {
        String className = ClassUtils.convertClassNameToResourcePath(classPath);
        URL url = classLoader.getResource(className);
        File file = new File(url.getPath());
        Set<File> fileSet = getFiles(file);
        Resource[] resources = new Resource[fileSet.size()];
        int i = 0;
        for (File newFile : fileSet) {
            resources[i++] = new FileSystemResource(newFile);
        }
        return resources;
    }

    private Set<File> getFiles(File file) {
        Set<File> fileSet = new HashSet<>(8);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.exists()) {
                if (file1.isDirectory()) {
                    getFiles(file);
                }else{
                    fileSet.add(file1);
                }
            }
        }
        return fileSet;
    }
}
