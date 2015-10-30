package cn.passionshark.project.community.common.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求幂等型Annotation, 有此标识的字段参加幂等检查.
 *
 * @author pierre
 * @version $ v1.0 Jan 15, 2015 $
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Idempotent {
}
