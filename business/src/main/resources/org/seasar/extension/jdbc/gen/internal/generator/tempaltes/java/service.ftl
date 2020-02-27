<#import "/lib.ftl" as lib>
<#if lib.copyright??>
${lib.copyright}
</#if>
<#if !lib.copyright??>
<#include "/copyright.ftl">
</#if>
<#if packageName??>
package ${packageName};
</#if>

<#list importNameSet as importName>
import ${importName};
</#list>
<#if staticImportNameSet?size gt 0>

  <#list staticImportNameSet as importName>
import static ${importName};
  </#list>
</#if>

<#global isUpdateService=true>
<#list idPropertyMetaList as prop><#if prop_has_next><#global isUpdateService=false></#if></#list>
<#if isUpdateService == true>
import org.seasar.extension.jdbc.where.SimpleWhere;
</#if>

import org.apache.commons.collections.CollectionUtils;
import com.google.common.collect.Lists;

/**
 * {@link ${shortEntityClassName}}のサービスクラスです。
 * 
<#if lib.author??>
 * @author ${lib.author}
</#if>
 */
@Generated(value = {<#list generatedInfoList as info>"${info}"<#if info_has_next>, </#if></#list>})
public class ${shortClassName} extends ${shortSuperclassName}<${shortEntityClassName}> {
<#if jdbcManagerSetterNecessary>

    /**
     * JDBCマネージャを設定します。
     * 
     * @param jdbcManager
     *            JDBCマネージャ
     */
    @Resource(name = "${jdbcManagerName}")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void setJdbcManager(JdbcManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }
</#if>
<#if idPropertyMetaList?size gt 0>

    /**
     * 識別子でエンティティを検索します。
     * 
  <#list idPropertyMetaList as prop>
     * @param ${prop.name} 識別子
  </#list>
     * @return エンティティ
     */
    public ${shortEntityClassName} findById(<#list idPropertyMetaList as prop>${prop.propertyClass.simpleName} ${prop.name}<#if prop_has_next>, </#if></#list>) {
        return select().id(<#list idPropertyMetaList as prop>${prop.name}<#if prop_has_next>, </#if></#list>).getSingleResult();
    }
<#if isUpdateService == true>   
    /**
     * 識別子で排他制御を行います。
     * 
  <#list idPropertyMetaList as prop>
     * @param ${prop.name} 識別子
  </#list>
     * @return エンティティ
     */
    public ${shortEntityClassName} forUpdate(<#list idPropertyMetaList as prop>${prop.propertyClass.simpleName} ${prop.name}<#if prop_has_next>, </#if></#list>) {
        return select().forUpdate().id(<#list idPropertyMetaList as prop>${prop.name}<#if prop_has_next>, </#if></#list>).getSingleResult();
    }
    
    /**
     * 識別子で排他制御を行います。
     * 
  <#list idPropertyMetaList as prop>
     * @param ${prop.name}List 識別子
  </#list>
     * @return エンティティのリスト
     */
    public List<${shortEntityClassName}> forUpdate(<#list idPropertyMetaList as prop>List<${prop.propertyClass.simpleName}> ${prop.name}List<#if prop_has_next>, </#if></#list>) {
        if (CollectionUtils.isEmpty(<#list idPropertyMetaList as prop>${prop.name}List<#if prop_has_next></#if></#list>)) {
            return Lists.newArrayList();
        }
        return select().forUpdate().where(new SimpleWhere().in(<#list idPropertyMetaList as prop>${prop.name}(), ${prop.name}List)<#if prop_has_next>.in(</#if></#list>).getResultList();
    }
</#if>
</#if>
<#-- バージョンで検索とかしなさそうなのでコメントアウト
<#if idPropertyMetaList?size gt 0 && versionPropertyMeta??>

    /**
     * 識別子とバージョン番号でエンティティを検索します。
     * 
  <#list idPropertyMetaList as prop>
     * @param ${prop.name} 識別子
  </#list>
     * @param ${versionPropertyMeta.name}
     *            バージョン番号
     * @return エンティティ
     */
    public ${shortEntityClassName} findByIdVersion(<#list idPropertyMetaList as prop>${prop.propertyClass.simpleName} ${prop.name}, </#list>${versionPropertyMeta.propertyClass.simpleName} ${versionPropertyMeta.name}) {
        return select().id(<#list idPropertyMetaList as prop>${prop.name}<#if prop_has_next>, </#if></#list>).version(${versionPropertyMeta.name}).getSingleResult();
    }
</#if>
-->
<#if namesModel?? && idPropertyMetaList?size gt 0>

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     * 
     * @return エンティティのリスト
     */
    public List<${shortEntityClassName}> findAllOrderById() {
        return select().orderBy(<#list idPropertyMetaList as prop>asc(${prop.name}())<#if prop_has_next>, </#if></#list>).getResultList();
    }
    
    
</#if>
}