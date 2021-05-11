<!DOCTYPE>
<html>
<head>
    <title>file system</title>
</head>
<body>
<#if dirList??>
    <ul>
        <#list dirList as dir>
            <li>${dir.absolutePath}
        </#list>
    </ul>
</#if>

<#if fileList??>
    <ul>
        <#list fileList as file>
            <li> ${file.absolutePath}
        </#list>
    </ul>
</#if>
</body>
</html>