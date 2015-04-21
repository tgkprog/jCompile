package s2n.spring.samples.redis1;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Hello world!
 *
 */
public class Redis1App {

	private static final String S1 = "S1";
	private static final String S2 = "S2";

	// RedisTemplate<Object, Object> redisTemplate;
	public static void main(String[] args) throws Exception {
		File f = null;
		try {
			f = new File("./");
			System.out.println("path " + f.getCanonicalPath());
			f = new File("beans.xml");
			System.out.println("2 path :" + f.getCanonicalPath() + "\nexists " + f.exists());
		} catch (Exception e) {
			System.out.println("err " + e);
		}
		System.out.println("Hello 1 b1");
		boolean useJavaConfig = true;
		ApplicationContext ctx = null;

		// Showing examples of both Xml and Java based configuration
		if (useJavaConfig) {
			ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		} else {
			ctx = new ClassPathXmlApplicationContext("beans.xml");
			// new Classpa
		}

		HelloService helloService = ctx.getBean("helloService", HelloService.class);

		// First method execution using key="Josh", not cached
		System.out.println("b23\nmessage: " + helloService.getMessage("B"));

		// Second method execution using key="Josh", still not cached
		System.out.println("message: " + helloService.getMessage("B"));

		slp(100);
		System.out.println("message: " + helloService.getMessage(S1));

		// Second method execution using key="S1", cached
		System.out.println("message: [" + helloService.getMessage(S1) + "]");
		System.out.println("message: " + helloService.getMessage("B"));
		System.out.println("Cac 4:");
		RedisTemplate<Object, Object> redisTemplate = ctx.getBean("redisTemplate", RedisTemplate.class);
		RedisTemplate<Object, Object> r = redisTemplate;
		// System.out.println("RT " + r.g);
		slp(100);
		CacheManager c = ctx.getBean("cacheManager", CacheManager.class);
		Collection<String> cc = c.getCacheNames();
		Iterator<String> ci = cc.iterator();
		System.out.println("cc : " + cc + ", ci " + ci.getClass());
		while (ci.hasNext()) {
			String s = ci.next();
			System.out.println("Cac : " + s);
		}
		Cache ca = c.getCache(AppConfig.CAC);
		slp(100);
		StringBuilder sb1 = new StringBuilder();
		Object ocv = ca.get(S1);
		sb1.append(ocv);
		if(ocv != null && ocv instanceof SimpleValueWrapper){
			sb1.append(" val ").append(((SimpleValueWrapper)ocv).get());
		}
		
		System.out.println("ca -S1- :" + sb1);
		//System.out.println("ca -S2- :" + ((SimpleValueWrapper) ca.get("S2")).get());
	}

	private static void slp(int i) {
		try {
			Thread.sleep(i);
		} catch (Exception e) {

		}
	}
}
