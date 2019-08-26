package org.chensj.learn;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResource;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * @author chensj
 * @title
 * @email chensj@winning.com.cn
 * @package org.chensj.learn
 * @date: 2018-11-26 下午9:08
 */
public class FirstDemo {
    public static void main(String[] args) {
        //fileTemplateResourceLoader();
        classpathTemplateResourceLoader();
    }

    /**
     * 模板资源加载器
     * 字符串模板加载器
     */
    public static void stringTemplateResourceLoader() {
        try {
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            Configuration config = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, config);
            Template template = gt.getTemplate("hello,${name}");
            template.binding("name", "Demo");
            String string = template.render();
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件资源模板加载器
     * 模板资源是以文件形式管理的，集中放在某一个文件目录下（如webapp的模板根目录就可能是WEB-INF/template里）
     */
    public static void fileTemplateResourceLoader() {
        try {
            String root = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "template";
            FileResourceLoader resourceLoader = new FileResourceLoader(root, "UTF-8");
            Configuration config = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, config);
            Template template = gt.getTemplate("01-hello.txt");
            String string = template.render();
            //System.out.println(root);
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Classpath资源模板加载器
     */
    public static void classpathTemplateResourceLoader() {
        try {
            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("template");
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate("/01-hello.txt");
            System.out.println(t.render());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
