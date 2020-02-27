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

/**
 * プロパティーファイルのキーを表すクラス。 <br />
 * ${fileName} より生成
 * このクラスは自動生成されています。上書きされるので編集しないでください。
 */
@Generated(value = { <#list generatedInfoList as info>"${info}"<#if info_has_next>, </#if></#list> })
public enum ${shortClassName} {

<#list values as prop>
    /** ${prop.comment} */
    ${prop.name}("${prop.key}")<#if prop_has_next>,<#else>;</#if>
</#list>

    public String key;
    
    public String getKey() {
        return key;
    }
    
    ${shortClassName}(String key) {
        this.key=key;
    }  
}