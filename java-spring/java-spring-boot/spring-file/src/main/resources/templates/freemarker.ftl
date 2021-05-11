<!DOCTYPE>
<html>
<head>
    <title>file system</title>
</head>
<body>
<#if dirList??>
    <ul>
        <#list dirList as dir>
            <li><a href="/?path=${dir.replacePath}"/>${dir.absolutePath}
        </#list>
    </ul>
</#if>

<#if fileList??>
    <ul>
        <#list fileList as file>
            <li><a href="/download?path=${file.replacePath}"><font color="#00FF00">${file.absolutePath}</font>
        </#list>
    </ul>
</#if>
</body>
</html>