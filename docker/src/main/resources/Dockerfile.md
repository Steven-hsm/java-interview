### ***\*文章目录\****

[Dockerfile参考（Dockerfile reference）](#DockerfileDockerfile_reference_4)

[用法（Usage）](#Usage_8)

[格式（Format）](#Format_66)

[解析器指令（Parser directives）](#Parser_directives_82)

[转义（escape）](#escape_129)

[环境更换（Environment replacement）](#Environment_replacement_198)

[.dockerignore文件](#dockerignore_243)

[FORM 指定基础镜像](#FORM__296)

[了解ARG和FROM如何互动](#ARGFROM_318)

[RUN 执行命令](#RUN__335)

[已知问题（RUN）](#RUN_372)

[CMD 设置默认命令](#CMD__379)

[LABEL 设置标签](#LABEL__413)

[MAINTAINER 设置作者(deprecated已弃用)](#MAINTAINER_deprecated_448)

[EXPOSE 声明端口](#EXPOSE__457)

[ENV 设置环境变量](#ENV__481)

[ADD 添加文件](#ADD__509)

[COPY 复制文件（ADD的简化版，能用COPY就不用ADD）](#COPY_ADDCOPYADD_570)

[ENTRYPOINT 配置容器入口点](#ENTRYPOINT__620)

[Exec形式ENTRYPOINT示例](#ExecENTRYPOINT_637)

[Shell形式ENTRYPOINT示例](#ShellENTRYPOINT_738)

[了解CMD和ENTRYPOINT如何相互作用](#CMDENTRYPOINT_792)

[VOLUME 创建外部卷](#VOLUME__807)

[有关指定卷的说明](#_822)

[USER 设置当前用户](#USER__831)

[WORKDIR 设置工作目录](#WORKDIR__848)

[ARG 构建参数](#ARG__870)

[Default values 默认值](#Default_values__887)

[Scope 作用域](#Scope__897)

[Using ARG variables 使用参数变量](#Using_ARG_variables__922)

[Predefined ARGs 预定义的ARG](#Predefined_ARGs_ARG_951)

[Inpact on build caching 对构建缓存的影响](#Inpact_on_build_caching__984)

[ONBUILD 为下游镜像添加触发器](#ONBUILD__1017)

[STOPSIGNAL 容器退出信号](#STOPSIGNAL__1049)

[HEALTHCHECK 健康检查](#HEALTHCHECK__1055)

[SHELL](#SHELL_1100)

[Dockerfile示例](#Dockerfile_1197)

## Dockerfile参考

Docker可以通过读取Dockerfile中的指令自动构建镜像。 Dockerfile是一个文本文档，其中包含用户可以在命令行上调用以装配镜像的所有命令。 使用`docker build`，用户可以创建一个连续执行多个命令行指令的自动构建。

本页介绍了可以在Dockerfile中使用的命令。 阅读完本页后，请参阅Dockerfile[最佳实践](https://docs.docker.com/engine/userguide/eng-image/dockerfile_best-practices/)以获取面向技巧的指南。

## 用法

`docker build`命令从Dockerfile和上下文构建镜像。 构建的上下文是指定位置PATH或URL处的文件集。 PATH是本地文件系统上的目录。 URL是Git存储库位置。

上下文是递归处理的。 因此，PATH包括任何子目录，URL包括存储库及其子模块。 此示例显示了使用当前目录作为上下文的构建命令：
```shell
$ docker build .
Sending build context to Docker daemon  6.51 MB
```

构建由Docker守护进程（daemon）运行，而不是由CLI运行。 构建过程的第一件事是将整个上下文（递归地）发送到守护进程。 在大多数情况下，最好从**空目录**开始作为上下文，并将Dockerfile保存在该目录中。 仅添加构建Dockerfile所需的文件。

>警告：不要将根目录/用作PATH，因为它会导致构建将硬盘驱动器的全部内容传输到Docker守护程序。

要在构建上下文中使用文件，Dockerfile引用指令中指定的文件，例如COPY指令。 要提高构建的性能，请通过将.dockerignore文件添加到上下文目录来排除文件和目录。 有关如何[创建.dockerignore文件](#dockerignore_242)的信息，请参阅此页面上的文档。

习惯上，Dockerfile称为Dockerfile，位于上下文的根目录中。 你可以将 -f 参数与docker build一起使用，以指向文件系统中任何位置的Dockerfile。

```shell
$ docker build -f /path/to/a/Dockerfile .
```

如果构建成功，你可以指定存储库和标记以保存新镜像：
```shell
$ docker build -t shykes/myapp .
```

要在构建后将映像标记为多个存储库，请在运行build命令时添加多个-t参数：
```shell
$ docker build -t shykes/myapp:1.0.2 -t shykes/myapp:latest .
```
在Docker守护进程运行Dockerfile中的指令之前，它会执行Dockerfile的初步验证，如果语法不正确则返回错误：
```shell
$ docker build -t test/myapp .
Sending build context to Docker daemon 2.048 kB
Error response from daemon: Unknown instruction: RUNCMD
```

Docker守护进程逐个运行Dockerfile中的指令，在必要时将每条指令的结果提交给新镜像，最后输出新镜像的ID。 Docker守护进程将自动清理你发送的上下文。

请注意，每条指令都是独立运行的，并且会导致创建新镜像 - 因此RUN cd / tmp对下一条指令不会产生任何影响。

只要有可能，Docker将重新使用中间镜像（缓存），以显著地加速docker build过程。 这由控制台输出中的Using cache消息指示。 （有关更多信息，请参阅Dockerfile最佳实践指南中的[构建缓存部分](#build-cache)）：
```shell
$ docker build -t svendowideit/ambassador .

Sending build context to Docker daemon 15.36 kB
Step 1/4 : FROM alpine:3.2
 ---> 31f630c65071
Step 2/4 : MAINTAINER SvenDowideit@home.org.au
 ---> Using cache
 ---> 2a1c91448f5f
Step 3/4 : RUN apk update &&    apk add socat &&     rm -r /var/cache/
 ---> Using cache
 ---> 21ed6e7fbb73
Step 4/4 : CMD env | grep _TCP= | (sed 's/.*_PORT_\([0-9]*\)_TCP=tcp:\/\/\(.*\):\(.*\)/socat -t 100000000 TCP4-LISTEN:\1,fork,reuseaddr TCP4:\2:\3 \&/' && echo wait) | sh
 ---> Using cache
 ---> 7ea8aef582cc
Successfully built 7ea8aef582cc
```

构建缓存仅用于具有本地父链的镜像。 这意味着这些镜像是由以前的版本创建的，或者整个镜像链都用`docker load`装载了。如果你希望使用特定镜像的构建缓存，可以使用`--cache-from`选项指定它。 使用`--cache-from`指定的镜像不需要具有父链，可以从其他注册表中提取。

完成构建后，你已准备好查看[将存储库推送到其注册表](#/contributing-to-docker-hub)。

## 格式

这是Dockerfile的格式：
```shell
# Comment
INSTRUCTION arguments
```

该指令不区分大小写。 但是，惯例是让它们成为大写的，以便更容易地将它们与参数区分开来。

Docker按顺序在Dockerfile中运行指令。` Dockerfile`必须以`FROM`指令开头。 FROM指令指定要构建的[基础镜像(Base Image)](#base-image)。`FROM`只能在一个或多个ARG指令之前，这些指令声明在Dockerfile中的FROM行中使用的参数。

Docker将以＃开头的行视为注释，除非该行是有效的[解析器指令(parser directive)](#Parser_directives_82)。 行中任何其他位置的＃标记都被视为参数。 允许这样的语句：
```shell
# Comment
RUN echo 'we are running some # of cool things'
```
注释中不支持行继续符。

## 解析器指令

解析器指令是可选的，并且会影响处理Dockerfile中后续行的方式。解析器指令不会向构建添加层，也不会显示为构建步骤。解析器指令以＃directive = value的形式编写为特殊类型的注释。单个指令只能使用一次。

一旦处理了注释，空行或构建器指令，Docker就不再查找解析器指令。相反，它将格式化为解析器指令的任何内容视为注释，并且不会尝试验证它是否可能是解析器指令。因此，所有解析器指令必须位于Dockerfile的最顶层。

解析器指令不区分大小写。但是，惯例是它们是小写的。约定还包括任何解析器指令后面的空行。解析器指令不支持行继续符。

由于这些规则，以下示例均无效：

由于行继续而无效：
```shell
# direc \
tive=value
```

由于出现两次无效：
```shell
# directive=value1
# directive=value2
FROM ImageName
```

由于在构建器指令后出现而被视为注释：
```shell
FROM ImageName
# directive=value
```

由于在注释之后出现而被视为注释，而不是解析器指令：
```shell
# About my dockerfile
# directive=value
FROM ImageName
```

由于未被识别，未知指令被视为注释。 此外，由于出现在注释之后，已知指令被视为注释，而不是解析器指令。
```shell
# unknowndirective=value
# knowndirective=value
```

解析器指令中允许使用非换行空格。 因此，以下几行都是相同的：
```shell
#directive=value
# directive =value
#	directive= value
# directive = value
#	  dIrEcTiVe=value
```

支持以下解析器指令：
* escape

## 转义
```shell
# escape=\ (backslash)
```
或
```shell
# escape=` (backtick)
```

escape指令设置用于转义Dockerfile中字符的字符。如果未指定，则默认转义字符为\。

转义字符既用于转义行中的字符，也用于转义换行符。这允许Dockerfile指令跨越多行。请注意，无论转义解析器指令是否包含在Dockerfile中，都不会在RUN命令中执行转义，除非在行尾。

将转义字符设置为`在Windows上特别有用，其中\是目录路径分隔符。 `与[Windows PowerShell](https://technet.microsoft.com/en-us/library/hh847755.aspx)一致。

请考虑以下示例，该示例在Windows上以非显而易见的方式失败。第二行末尾的第二个\将被解释为换行符的转义符，而不是第一个\的转义目标。类似地，假设它实际上作为指令处理，第三行末尾的\将导致它被视为行继续。这个dockerfile的结果是第二行和第三行被认为是单个指令：
```shell
FROM microsoft/nanoserver
COPY testfile.txt c:\\
RUN dir c:\
```
结果是：
```shell
PS C:\John> docker build -t cmd .
Sending build context to Docker daemon 3.072 kB
Step 1/2 : FROM microsoft/nanoserver
 ---> 22738ff49c6d
Step 2/2 : COPY testfile.txt c:\RUN dir c:
GetFileAttributesEx c:RUN: The system cannot find the file specified.
PS C:\John>
```

上面的一个解决方案是使用/作为COPY指令和dir的目标。 但是，这种语法充其量是令人困惑的，因为它对于Windows上的路径来说并不自然，并且最坏的情况是，由于Windows上的所有命令都不支持/作为路径分隔符，因此容易出错。

通过添加转义(escape)解析器指令，以下Dockerfile按预期成功使用Windows上的自然平台语义文件路径：
```shell
# escape=`
FROM microsoft/nanoserver
COPY testfile.txt c:\
RUN dir c:\
```

结果是：
```shell
PS C:\John> docker build -t succeeds --no-cache=true .
Sending build context to Docker daemon 3.072 kB
Step 1/3 : FROM microsoft/nanoserver
 ---> 22738ff49c6d
Step 2/3 : COPY testfile.txt c:\
 ---> 96655de338de
Removing intermediate container 4db9acbb1682
Step 3/3 : RUN dir c:\
 ---> Running in a2c157f842f5
 Volume in drive C has no label.
 Volume Serial Number is 7E6D-E0F7
 Directory of c:\
10/05/2016  05:04 PM       1,894 License.txt
10/05/2016  02:22 PM   <DIR>      Program Files
10/05/2016  02:14 PM   <DIR>      Program Files (x86)
10/28/2016  11:18 AM         62 testfile.txt
10/28/2016  11:20 AM   <DIR>      Users
10/28/2016  11:20 AM   <DIR>      Windows
​      2 File(s)      1,956 bytes
​      4 Dir(s)  21,259,096,064 bytes free
 ---> 01c7f3bef04f
Removing intermediate container a2c157f842f5
Successfully built 01c7f3bef04f
PS C:\John>
```

## 环境更换

环境变量（[使用ENV语句](#ENV__481)声明）也可以在某些指令中用作Dockerfile要解释的变量。还会处理转义，以便将类似变量的语法包含在字面上。

使用$variable_name或${variable_name}在Dockerfile中标注环境变量。它们被等效地处理，并且括号语法通常用于解决具有没有空格的变量名称的问题，例如${foo}_bar。

${variable_name}语法还支持以下指定的一些标准bash修饰符：

· ${variable:-word}表示如果设置了variable，那么结果将是该值。如果未设置variable，那么word将是结果。

· ${variable:+word}表示如果设置了variable，那么word将是结果，否则结果是空字符串。
在所有情况下，word可以是任何字符串，包括其他环境变量。

可以通过在变量之前添加\来进行转义：\$foo或\${foo}，例如，将分别转换为$foo和${foo}文字。

示例（在＃之后显示解析的表示）：
```shell
FROM busybox
ENV foo /bar
WORKDIR ${foo}  # WORKDIR /bar
ADD . $foo    # ADD . /bar
COPY \$foo /quux # COPY $foo /quux
```

Dockerfile中的以下指令列表支持环境变量：

* ADD
* COPY
* ENV
* EXPOSE
* FROM
* LABEL
* STOPSIGNAL
* USER
* VOLUME
* WORKDIR
以及：
* ONBUILD（当与上面支持的指令之一结合使用时）

注意：在1.4之前，ONBUILD指令**不支持环境变量**，即使与上面列出的任何指令结合使用也是如此。

环境变量替换将在整个指令中对每个变量使用相同的值。 换句话说，在这个例子中：
```shell
ENV abc=hello
ENV abc=bye def=$abc
ENV ghi=$abc
```
将导致def具有hello值，而不是bye。但是，ghi将具有bye值，因为它不是将abc设置为bye的相同指令的一部分。

## .dockerignore文件
在docker CLI将上下文发送到docker守护进程之前，它会在上下文的根目录中查找名为.dockerignore的文件。如果此文件存在，CLI将修改上下文以排除与其中的模式匹配的文件和目录。这有助于避免不必要地将大型或敏感文件和目录发送到守护进程，并可能使用ADD或COPY将它们添加到镜像。

CLI将.dockerignore文件解释为新行分隔的模式列表，类似于Unix shell的文件globs。出于匹配的目的，上下文的根被认为是工作目录和根目录。例如，模式 /foo/bar和foo/bar都会在PATH的foo子目录中或位于URL的git存储库的根目录中排除名为bar的文件或目录。两者都不包括任何其他内容。

如果.dockerignore文件中的一行的第1列以＃开头，则此行被视为注释，并在CLI解释之前被忽略。

这是一个示例.dockerignore文件：
```shell
# comment
*/temp*
*/*/temp*
temp?
```
此文件导致以下构建行为：

| ***\*规则\**** | ***\*行为\****                                               |
| -------------- | ------------------------------------------------------------ |
| # 注释         | 忽略。                                                       |
| */temp*        | 在根的任何直接子目录中排除名称以temp开头的文件和目录。 例如，排除普通文件/somedir/temporary.txt，目录/somedir/temp也是如此。 |
| \*/\*/temp*      | 从根目录下两级的任何子目录中排除以temp开头的文件和目录。 例如，排除了/somedir/subdir/temporary.txt。 |
| temp?          | 排除根目录中的文件和目录，其名称是temp的单字符扩展。 例如，排除/tempa和/tempb。 |

匹配是使用Go的[filepath.Match](#Match)规则完成的。 预处理步骤使用Go的[filepath.Clean](#Clean)删除前导和尾随空格并消除. 和..元素。 预处理后为空行将被忽略。

除了Go的filepath.Match规则，Docker还支持一个特殊的通配符字符串**，它匹配任意数量的目录（包括零）。 例如，**/*.go将排除在所有目录中找到的以.go结尾的所有文件，包括构建上下文的根目录。

!（感叹号）开头的行可用于排除例外情况。 以下是使用此机制的示例.dockerignore文件：
```shell
 *.md
  !README.md
```
除README.md之外的所有markdown文件都将从上下文中排除。

!例外规则的位置会影响行为：与特定文件匹配的.dockerignore的最后一行确定是包含还是排除。 请考虑以下示例：
```shell
*.md
!README*.md
README-secret.md
```

除了README-secret.md以外的README文件，上下文中不包含markdown文件。

现在考虑这个例子：
```shell
*.md
README-secret.md
!README*.md
```

包含所有README文件。 中间行没有效果，因为!README*.md匹配README-secret.md并且是最后一个。

你甚至可以使用.dockerignore文件来排除Dockerfile和.dockerignore文件。 这些文件仍然发送到守护程序，因为它需要它们来完成它的工作。 但ADD和COPY指令不会将它们复制到镜像中。

最后，你可能希望指定要包含在上下文中的文件，而不是要排除的文件。 要实现此目的，请将*指定为第一个模式，然后指定一个或多个模式! 例外模式。

> 注意：由于历史原因，模式. 被忽略了。

## FORM 指定基础镜像
```shell
FROM <image> [AS <name>]
```
或
```shell
FROM <image>[:<tag>] [AS <name>]
```
或
```shell
FROM <image>[@<digest>] [AS <name>]
```
FROM指令初始化新的构建阶段并为后续指令设置[基础镜像](#base-image)。因此，有效的Dockerfile必须以FROM指令开头。镜像可以是任何有效镜像 - 通过从[公共存储库](https://docs.docker.com/engine/tutorials/dockerrepos/)中**提取镜像**（pulling an image）来启动它尤其容易。

ARG是Dockerfile中唯一可以在FROM之前的指令。请参阅[了解ARG和FROM如何交互](#ARGFROM_318)。

FROM可以在单个Dockerfile中多次出现以创建多个镜像，或者使用一个构建阶段作为另一个构建阶段的依赖项。只需在每个新的FROM指令之前记下提交输出的最后一个图像ID。每个FROM指令清除先前指令创建的任何状态。

通过将AS name添加到FROM指令，可以将可选的名称赋予新的构建阶段。该名称可以在后续的FROM和COPY --from=<name|index>指令中使用，以引用此阶段构建的镜像。

tag（标记）或digest（摘要）值是可选的。如果省略其中任何一个，则构建器默认采用latest（最新）标记。如果找不到tag值，构建器将返回错误。

### 了解ARG和FROM如何互动

FROM指令支持在第一个FROM之前发生的任何ARG指令声明的变量。
```shell
ARG  CODE_VERSION=latest
FROM base:${CODE_VERSION}
CMD  /code/run-app

FROM extras:${CODE_VERSION}
CMD  /code/run-extras
```
在FROM之前声明的ARG在构建阶段之外，因此在FROM之后的任何指令中都不能使用它。 要使用在第一个FROM之前声明的ARG的默认值，请在构建阶段内使用没有值的ARG指令：
```shell
ARG VERSION=latest
FROM busybox:$VERSION
ARG VERSION
RUN echo $VERSION > image_version
```
## RUN 执行命令

RUN有两种形式：

* RUN <command> (shell形式，运行在shell的命令, 在Linux上默认为/bin/sh -c或在Windows上为cmd /S /C)
* RUN ["executable", "param1", "param2"] (exec执行程序形式)

RUN指令将在当前镜像之上的新层中执行任何命令并提交结果。 生成的已提交镜像将用于Dockerfile中的下一步。

分层的RUN指令和生成提交符合Docker的核心概念，其中提交很便宜，并且可以从镜像历史中的任何点创建容器，就像源代码控制一样。

**exec**（执行程序）形式可以避免shell字符串重写，并使用不包含指定shell可执行文件的基础镜像来运行RUN命令。

可以使用SHELL命令更改**shell**形式的默认**shell**。

在**shell**形式中，你可以使用\（反斜杠）将单个RUN指令继续到下一行。 例如，考虑以下两行：
```shell
RUN /bin/bash -c 'source $HOME/.bashrc; \
echo $HOME'
```

它们一起相当于这一行：
```shell
RUN /bin/bash -c 'source $HOME/.bashrc; echo $HOME'
```

注意：要使用除“/bin/sh”之外的其他shell，请使用传入所需shell的exec形式。例如，RUN ["/bin/bash", "-c", "echo hello"]

注意：exec形式被解析为JSON数组，这意味着你必须使用双引号（"）来围绕单词而不是单引号（’）。

注意：与shell形式不同，exec形式不会调用命令shell。这意味着不会发生正常的shell处理。例如，RUN [ "echo", "$HOME" ]不会对$HOME执行变量替换。如果你想要shell处理，那么要么使用shell形式，要么直接执行shell，例如：RUN [ "sh", "-c", "echo $HOME" ]。当使用exec形式并直接执行shell时，就像shell形式的情况一样，它是执行环境变量扩展的shell，而不是docker。

> 注意：在JSON表单中，必须转义反斜杠。这在反斜杠是路径分隔符的Windows上尤为重要。由于不是有效的JSON，以下行将被视为shell表单，并以意外方式失败：RUN ["c:\windows\system32\tasklist.exe"]。此示例的正确语法是：RUN ["c:\\windows\\system32\\tasklist.exe"]

RUN指令的缓存在下一次构建期间不会自动失效。像RUN apt-get dist-upgrade -y这样的指令的缓存将在下一次构建期间重用。可以使用--no-cache标志使RUN指令的缓存无效，例如docker build --no-cache。

有关详细信息，请参阅[Dockerfile最佳实践指南](#/build-cache)。

ADD指令可以使RUN指令的缓存无效。请参阅[下文](#ADD__509)了解详情。

### 已知问题（RUN
[问题783](https://github.com/docker/docker/issues/783)是关于使用AUFS文件系统时可能发生的文件权限问题。 例如，在尝试rm文件时，你可能会注意到它。

对于具有最近aufs版本的系统（即，可以设置dirperm1挂载选项），docker将尝试通过使用dirperm1选项安装层来自动修复问题。 有关dirperm1选项的更多详细信息，请参见[aufs手册页](https://github.com/sfjro/aufs3-linux/tree/aufs3.18/Documentation/filesystems/aufs)

如果你的系统不支持dirperm1，则问题描述了一种解决方法。

## CMD 设置默认命令

CMD指令有三种形式：

* CMD ["executable","param1","param2"] (exec形式，这是首选形式)
* CMD ["param1","param2"] (作为ENTRYPOINT的默认参数)
* CMD command param1 param2 (shell形式)

Dockerfile中只能有**一条CMD指令**。如果列出多个CMD，则只有最后一个CMD才会生效。

CMD的主要目的是为正在执行的容器提供默认命令。这些默认命令可以包含可执行文件，也可以省略可执行文件，在这种情况下，你还必须指定ENTRYPOINT指令。

注意：如果使用CMD为ENTRYPOINT指令提供默认参数，则应使用JSON数组格式指定CMD和ENTRYPOINT指令。

注意：exec形式解析为JSON数组，这意味着你必须使用双引号（"）来围绕单词而不是单引号（’）。

注意：与shell形式不同，exec形式不会调用命令shell。这意味着不会发生正常的shell处理。例如，RUN [ "echo", "$HOME" ]不会对$HOME执行变量替换。如果你想要shell处理，那么要么使用shell形式，要么直接执行shell，例如：RUN [ "sh", "-c", "echo $HOME" ]。当使用exec形式并直接执行shell时，就像shell形式的情况一样，它是执行环境变量扩展的shell，而不是docker。

在shell或exec格式中使用时，CMD指令设置运行镜像时要执行的命令。

如果你使用CMD的shell形式，那么<command>将在/bin/sh -c中执行：
```shell
FROM ubuntu
CMD echo "This is a test." | wc -
```

如果要在没有shell的情况下运行<command>，则必须将该命令表示为JSON数组，并提供可执行文件的完整路径。此数组形式是CMD的首选格式。 任何其他参数必须在数组中单独表示为字符串：
```shell
FROM ubuntu
CMD ["/usr/bin/wc","--help"]
```

如果你希望容器每次都运行相同的可执行文件，那么你应该考虑将ENTRYPOINT与CMD结合使用。 请参阅[ENTRYPOINT](#ENTRYPOINT__620)。

如果用户指定了docker run的参数，那么它们将覆盖CMD中指定的默认命令。

注意：不要将RUN与CMD混淆。 RUN实际上运行一个命令并提交结果; CMD在构建时不执行任何操作，但指定了镜像的预期命令。

## LABEL 设置标签

* BEL <key>=<value> <key>=<value> <key>=<value> ...

LABEL指令将元数据添加到镜像。 LABEL是键值对。 要在LABEL值中包含空格，请使用引号和反斜杠，就像在命令行解析中一样。 一些用法示例：
```shell
LABEL "com.example.vendor"="ACME Incorporated"
LABEL com.example.label-with-value="foo"
LABEL version="1.0"
LABEL description="This text illustrates \
that label-values can span multiple lines."
```

镜像可以有多个标签。 你可以在一行中指定多个标签。 在Docker 1.10之前，这减小了最终镜像的大小，但现在不再是这种情况了。 你仍然可以选择在单个指令中指定多个标签，方法有以下两种：
```shell
LABEL multi.label1="value1" multi.label2="value2" other="value3"
```
```shell
LABEL multi.label1="value1" \
multi.label2="value2" \
other="value3"
```

基础或父镜像中包含的标签（FROM行中的镜像）由你的镜像继承。 如果标签已存在但具有不同的值，则最近适用的值将覆盖任何先前设置的值。

要查看镜像的标签，请使用`docker inspect`命令。
```shell
"Labels": {
  "com.example.vendor": "ACME Incorporated"
  "com.example.label-with-value": "foo",
  "version": "1.0",
  "description": "This text illustrates that label-values can span multiple lines.",
  "multi.label1": "value1",
  "multi.label2": "value2",
  "other": "value3"
},
```

## MAINTAINER 设置作者(deprecated已弃用)

MAINTAINER <name>

MAINTAINER指令设置生成镜像的Author（作者）字段。 LABEL指令是一个更加灵活的版本，你应该使用它，因为它可以设置你需要的任何元数据，并且可以轻松查看，例如使用docker inspect。 要设置与MAINTAINER字段对应的标签，你可以使用：
```shell
LABEL maintainer="SvenDowideit@home.org.au"
```
然后，这将通过docker inspect与其他标签一起显示。

## EXPOSE 声明端口

* EXPOSE <port> [<port>/<protocol>...]

EXPOSE指令通知Docker容器在运行时侦听指定的网络端口。 你可以指定端口是侦听TCP还是UDP，如果未指定协议，则默认为TCP。

EXPOSE指令实际上不发布端口。 它作为构建镜像的人和运行容器的人之间的一种文档，用于计划要发布的端口。 要在运行容器时实际发布端口，请在docker run上使用**-p**标志发布和映射一个或多个端口，或使用-P标志发布所有公开的端口并将它们映射到高阶(high-order)端口。

默认情况下，EXPOSE假定为TCP。 你还可以指定UDP：
```shell
EXPOSE 80/udp
```

要同时公开TCP和UDP，请包含两行：
```shell
EXPOSE 80/tcp
EXPOSE 80/udp
```

在这种情况下，如果将-P与`docker run`一起使用，则端口将针对TCP公开一次，针对UDP公开一次。 请记住，-P在主机上使用短暂的高阶主机端口，因此TCP和UDP的端口不同。

无论EXPOSE设置如何，你都可以使用-p标志在运行时覆盖它们。 例如
```shell
docker run -p 80:80/tcp -p 80:80/udp ...
```

要在主机系统上设置端口重定向，请参阅[使用-P标志](#expose-incoming-ports)。 docker network命令支持创建用于容器之间通信的网络，而无需公开或发布特定端口，因为连接到网络的容器可以通过任何端口相互通信。 有关详细信息，请参阅[此功能的概述](https://docs.docker.com/engine/userguide/networking/)）。

## ENV 设置环境变量
```shell
ENV <key> <value>
ENV <key>=<value>
```

ENV指令将环境变量<key>设置为值<value>。 此值将存在于构建阶段中所有后续指令的环境中，并且也可以在许多时候[内部替换](#Environment_replacement_198)。

ENV指令有两种形式。 第一种形式ENV <key> <value>，将单个变量设置为一个值。 第一个空格后面的整个字符串将被视为<value> - 包括空格字符。 该值将针对其他环境变量进行解释，因此如果未对其进行转义，则将删除引号字符。

第二种形式ENV <key>=<value> ...允许一次设置多个变量。 请注意，第二种形式在语法中使用等号(=)，而第一种形式则不然。 与命令行解析一样，引号和反斜杠可用于在值内包含空格。

例如：
```shell
ENV myName="John Doe" myDog=Rex\ The\ Dog \
myCat=fluffy
```
和
```shell
ENV myName John Doe
ENV myDog Rex The Dog
ENV myCat fluffy
```

将在最终镜像中产生相同的净结果。

当从生成的镜像运行容器时，使用ENV设置的环境变量将保持不变。 你可以使用docker inspect查看值，并使用docker run --env <key>=<value>更改它们。

注意：环境变量持久性可能会导致意外的副作用。 例如，设置ENV DEBIAN_FRONTEND noninteractive可能会使基于Debian的镜像上的apt-get用户感到困惑。 要为单个命令设置值，请使用RUN <key>=<value> <command>。

## ADD 添加文件

ADD有两种形式：

* ADD [--chown=<user>:<group>] <src>... <dest>
* ADD [--chown=<user>:<group>] ["<src>",... "<dest>"] （这种形式要求包含空格的路径）

注意：--chown功能仅在用于构建Linux容器的Dockerfiles上受支持，并且不适用于Windows容器。由于用户和组所有权概念不能在Linux和Windows之间进行转换，因此使用/etc/passwd和/etc/group将用户名和组名转换为ID会限制此功能仅适用于基于Linux OS的容器。

ADD指令从<src>复制新文件、目录或远程文件URL，并将它们添加到镜像的文件系统<dest>路径中。

可以指定多个<src>资源，但如果它们是文件或目录，则它们的路径将被解释为相对于构建上下文的源。

每个<src>可能包含通配符，匹配将使用Go的[filepath.Match](#Match)规则完成。 例如：
```shell
ADD hom* /mydir/     # adds all files starting with "hom"
ADD hom?.txt /mydir/   # ? is replaced with any single character, e.g., "home.txt"
```
<dest>是绝对路径，或相对于WORKDIR的路径，源将在目标容器中复制到该路径中。
```shell
ADD test relativeDir/      # adds "test" to `WORKDIR`/relativeDir/
ADD test /absoluteDir/     # adds "test" to /absoluteDir/
```

添加包含特殊字符（例如[和]）的文件或目录时，需要按照Golang规则转义这些路径，以防止它们被视为匹配模式。 例如，要添加名为arr[0].txt的文件，请使用以下命令：
```shell
ADD arr[[]0].txt /mydir/   # copy a file named "arr[0].txt" to /mydir/
```
除非可选的--chown标志指定给定用户名、组名或UID/GID组合以请求添加内容的特定所有权，否则将使用UID和GID为0创建所有新文件和目录。 --chown标志的格式允许用户名和组名字符串或任意组合的直接整数UID和GID。 提供没有组名的用户名或没有GID的UID将使用与GID相同的数字UID。 如果提供了用户名或组名，则容器的根文件系统/etc/passwd和/etc/group文件将分别用于执行从名称到整数UID或GID的转换。 以下示例显示了--chown标志的有效定义：
```shell
ADD --chown=55:mygroup files* /somedir/
ADD --chown=bin files* /somedir/
ADD --chown=1 files* /somedir/
ADD --chown=10:11 files* /somedir/
```

如果容器根文件系统不包含/etc/passwd或/etc/group文件，并且在--chown标志中使用了用户名或组名，则构建将在ADD操作上失败。使用数字ID不需要查找，也不依赖于容器根文件系统内容。

在<src>是远程文件URL的情况下，目标将具有600的权限。如果正在检索的远程文件具有HTTP Last-Modified头，则该头的时间戳将用于设置目标上的mtime。文件。但是，与ADD期间处理的任何其他文件一样，mtime将不包含在确定文件是否已更改且应更新缓存中。

注意：如果通过将Dockerfile传递给STDIN（docker build - < somefile）来构建，则没有构建上下文，因此Dockerfile只能包含基于URL的ADD指令。你还可以通过STDIN传递压缩存档：（docker build - < archive.tar.gz），Dockerfile`在存档根目录下，存档的其余部分将用作构建的上下文。

注意：如果你的URL文件使用身份验证进行保护，则需要使用RUN wget，RUN curl或使用容器内的其他工具，因为ADD指令不支持身份验证。

注意：如果<src>的内容已更改，则第一个遇到的ADD指令将使来自Dockerfile的所有后续指令的缓存无效。这包括使RUN指令的缓存无效。有关详细信息，请参阅[Dockerfile最佳实践指南](#/build-cache)。

ADD遵守以下规则：
* <src>路径必须位于构建的上下文中；你不能ADD ../something/something，因为docker构建的第一步是将上下文目录（和子目录）发送到docker守护进程。
* 如果<src>是URL且<dest>不以尾部斜杠结尾，则从URL下载文件并将其复制到<dest>。
* 如果<src>是URL并且<dest>以尾部斜杠结尾，则从URL推断文件名，并将文件下载到<dest>/<filename>。例如，ADD http://example.com/foobar /将创建文件/foobar。 URL必须具有有意义的路径，以便在这种情况下可以发现适当的文件名（http://example.com将不起作用）。
* 如果<src>是目录，则复制目录的全部内容，包括文件系统元数据。
* 注意：不复制目录本身，只复制其内容。
* 如果<src>是可识别的压缩格式（identity、gzip、bzip2或xz）的**本地**tar存档，则将其解压缩为目录。**远程**URL中的资源***\*不\****解压缩。复制或解压缩目录时，它与tar -x具有相同的行为，结果是：无论在目的地路径上存在什么，

源目录树的内容，在逐个文件的基础上解决有利于“2.”的冲突。

注意：文件是否被识别为可识别的压缩格式仅基于文件的内容而不是文件的名称来完成。例如，如果空文件恰好以.tar.gz结尾，则不会将其识别为压缩文件，也不会生成任何类型的解压缩错误消息，而是将文件简单地复制到目标。

如果<src>是任何其他类型的文件，则将其与元数据一起单独复制。在这种情况下，如果<dest>以尾部斜杠/结束，则将其视为目录，<src>的内容将写入<dest>/base(<src>)。

如果直接或由于使用通配符指定了多个<src>资源，则<dest>必须是目录，并且必须以斜杠/结尾。

如果<dest>不以尾部斜杠结束，则它将被视为常规文件，<src>的内容将写入<dest>。

如果<dest>不存在，则会在其路径中创建所有缺少的目录。

## COPY 复制文件（ADD的简化版，能用COPY就不用ADD

COPY有两种形式：
* COPY [--chown=<user>:<group>] <src>... <dest>
* COPY [--chown=<user>:<group>] ["<src>",... "<dest>"]（这种形式要求包含空格的路径）

注意：--chown功能仅在用于构建Linux容器的Dockerfiles上受支持，并且不适用于Windows容器。 由于用户和组所有权概念不能在Linux和Windows之间进行转换，因此使用/etc/passwd和/etc/group将用户名和组名转换为ID会限制此功能仅适用于基于Linux OS的容器。

COPY指令从<src>复制新文件或目录，并将它们添加到容器的文件系统的<dest>路径中。

可以指定多个<src>资源，但文件和目录的路径将被解释为相对于构建上下文的源。

每个<src>可能包含通配符，匹配将使用Go的[filepath.Match](#Match)规则完成。 例如：
```shell
COPY hom* /mydir/     # adds all files starting with "hom"
COPY hom?.txt /mydir/   # ? is replaced with any single character, e.g., "home.txt"
```

<dest>是绝对路径，或相对于WORKDIR的路径，源将在目标容器中复制到该路径中。
```shell
COPY test relativeDir/  # adds "test" to `WORKDIR`/relativeDir/
COPY test /absoluteDir/  # adds "test" to /absoluteDir/
```

添加包含特殊字符（例如[和]）的文件或目录时，需要按照Golang规则转义这些路径，以防止它们被视为匹配模式。 例如，要添加名为arr[0].txt的文件，请使用以下命令：
```shell
COPY arr[[]0].txt /mydir/   # copy a file named "arr[0].txt" to /mydir/
```

除非可选的--chown标志指定给定用户名、组名或UID/GID组合以请求添加内容的特定所有权，否则将使用UID和GID为0创建所有新文件和目录。 --chown标志的格式允许用户名和组名字符串或任意组合的直接整数UID和GID。 提供没有组名的用户名或没有GID的UID将使用与GID相同的数字UID。 如果提供了用户名或组名，则容器的根文件系统/etc/passwd和/etc/group文件将分别用于执行从名称到整数UID或GID的转换。 以下示例显示了--chown标志的有效定义：
```shell
COPY --chown=55:mygroup files* /somedir/
COPY --chown=bin files* /somedir/
COPY --chown=1 files* /somedir/
COPY --chown=10:11 files* /somedir/
```

如果容器根文件系统不包含/etc/passwd或/etc/group文件，并且在--chown标志中使用了用户名或组名，则构建将在COPY操作上失败。使用数字ID不需要查找，也不依赖于容器根文件系统内容。

注意：如果通过将Dockerfile传递给STDIN（docker build - < somefile）来构建，则没有构建上下文，因此COPY不能使用。

COPY接受一个可选标志--from=<name|index>，可用于将源位置设置为先前的构建阶段（使用FROM .. AS <name>创建），而不是由用户发送的构建上下文。该标志还接受使用FROM指令启动的所有先前构建阶段分配的数字索引。如果找不到具有指定名称的构建阶段，则尝试使用具有相同名称的镜像。

COPY遵守以下规则：
* <src>路径必须位于构建的上下文中；你不能COPY ../something/something，因为docker build的第一步是将上下文目录（和子目录）发送到docker守护进程。
* 如果<src>是目录，则复制目录的全部内容，包括文件系统元数据。
* 注意：不复制目录本身，只复制其内容。
* 如果<src>是任何其他类型的文件，则将其与元数据一起单独复制。在这种情况下，如果<dest>以尾部斜杠/结束，则将其视为目录，<src>的内容将写入<dest>/base(<src>)。
* 如果直接或由于使用通配符指定了多个<src>资源，则<dest>必须是目录，并且必须以斜杠/结尾。
* 如果<dest>不以尾部斜杠结束，则它将被视为常规文件，<src>的内容将写入<dest>。
* 如果<dest>不存在，则会在其路径中创建所有缺少的目录。

## ENTRYPOINT 配置容器入口点

ENTRYPOINT有两种形式:
* ENTRYPOINT ["executable", "param1", "param2"] (exec形式, 首选)
* ENTRYPOINT command param1 param2 (shell形式)
* ENTRYPOINT允许你配置将作为可执行文件运行的容器。

例如，以下将使用其默认内容启动nginx，侦听端口80：
```shell
docker run -i -t --rm -p 80:80 nginx
```

docker run <image>的命令行参数将在exec中的所有元素形成ENTRYPOINT后附加，并将覆盖使用CMD指定的所有元素。这允许将参数传递给入口点，即docker run <image> -d将-d参数传递给入口点。你可以使用docker run --entrypoint标志覆盖ENTRYPOINT指令。

shell形式阻止使用任何CMD或run命令行参数，但缺点是ENTRYPOINT将作为/bin/sh -c的子命令启动，它不传递信号。这意味着可执行程序不是容器的PID 1 - 并且**不会接收**Unix信号 - 因此你的可执行程序将不会从docker stop <container>接收SIGTERM。

只有Dockerfile中的最后一个ENTRYPOINT指令才会生效。

### Exec形式ENTRYPOINT示例

你可以使用ENTRYPOINT的exec形式设置相当稳定的默认命令和参数，然后使用任一形式的CMD来设置更可能更改的其他默认值。
```shell
FROM ubuntu
ENTRYPOINT ["top", "-b"]
CMD ["-c"]
```

运行容器时，你可以看到top是唯一的进程：
```shell
$ docker run -it --rm --name test  top -H
top - 08:25:00 up  7:27,  0 users,  load average: 0.00, 0.01, 0.05
Threads:  1 total,  1 running,  0 sleeping,  0 stopped,  0 zombie
%Cpu(s):  0.1 us,  0.1 sy,  0.0 ni, 99.7 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
KiB Mem:  2056668 total,  1616832 used,  439836 free,   99352 buffers
KiB Swap:  1441840 total,     0 used,  1441840 free.  1324440 cached Mem
PID USER    PR  NI   VIRT   RES   SHR S %CPU %MEM   TIME+ COMMAND
1 root    20  0  19744  2336  2080 R  0.0  0.1  0:00.04 top
```

要进一步检查结果，可以使用docker exec：
```shell
$ docker exec -it test ps aux
USER    PID %CPU %MEM   VSZ  RSS TTY    STAT START  TIME COMMAND
root     1  2.6  0.1  19752  2352 ?     Ss+  08:24  0:00 top -b -H
root     7  0.0  0.1  15572  2164 ?     R+  08:25  0:00 ps aux
```

并且你可以优雅地请求使用docker stop test来关闭top。
以下Dockerfile显示使用ENTRYPOINT在前台运行Apache（即，作为PID 1）：
```shell
FROM debian:stable
RUN apt-get update && apt-get install -y --force-yes apache2
EXPOSE 80 443
VOLUME ["/var/www", "/var/log/apache2", "/etc/apache2"]
ENTRYPOINT ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]
```
如果需要为单个可执行文件编写启动脚本，可以使用exec和gosu命令确保最终的可执行文件接收Unix信号：
```shell
**#!/usr/bin/env bash**set -e
if [ "$1" = 'postgres' ]; then
  chown -R postgres "$PGDATA"
  if [ -z "$(ls -A "$PGDATA")" ]; then
​    gosu postgres initdb
  fi
  exec gosu postgres "$@"fi
exec "$@"
```

最后，如果你需要在关机时进行一些额外的清理（或与其他容器通信），或者协调多个可执行文件，你可能需要确保ENTRYPOINT脚本接收Unix信号，传递它们，然后做一些工作：
```shell
#!/bin/sh**# Note: I've written this using sh so it works in the busybox container too**
**# USE the trap if you need to also do manual cleanup after the service is stopped,#   or need to start multiple services in the one container**trap "echo TRAPed signal" HUP INT QUIT TERM
**# start service in background here**
/usr/sbin/apachectl start
echo "[hit enter key to exit] or run 'docker stop <container>'"read
**# stop service and clean up here**echo "stopping apache"
/usr/sbin/apachectl stop
echo "exited $0"
```

如果使用docker run -it --rm -p 80:80 --name test apache运行此镜像，则可以使用docker exec或docker top检查容器的进程，然后让脚本停止Apache：
```shell
$ docker exec -it test ps aux
USER    PID %CPU %MEM   VSZ  RSS TTY    STAT START  TIME COMMAND
root     1  0.1  0.0  4448  692 ?     Ss+  00:42  0:00 /bin/sh /run.sh 123 cmd cmd2
root     19  0.0  0.2  71304  4440 ?     Ss  00:42  0:00 /usr/sbin/apache2 -k start
www-data   20  0.2  0.2 360468  6004 ?     Sl  00:42  0:00 /usr/sbin/apache2 -k start
www-data   21  0.2  0.2 360468  6000 ?     Sl  00:42  0:00 /usr/sbin/apache2 -k start
root     81  0.0  0.1  15572  2140 ?     R+  00:44  0:00 ps aux
$ docker top test
PID         USER         COMMAND
10035        root         {run.sh} /bin/sh /run.sh 123 cmd cmd2
10054        root         /usr/sbin/apache2 -k start
10055        33          /usr/sbin/apache2 -k start
10056        33          /usr/sbin/apache2 -k start
$ /usr/bin/time docker stop testtest
real	0m 0.27s
user	0m 0.03s
sys		0m 0.03s
```

注意：你可以使用--entrypoint覆盖ENTRYPOINT设置，但这只能将二进制设置为exec（不会使用sh -c）。

注意：exec形式被解析为JSON数组，这意味着你必须使用双引号（"）来围绕单词而不是单引号（’）。

注意：与shell形式不同，exec形式不会调用命令shell。这意味着不会发生正常的shell处理。例如，ENTRYPOINT [ "echo", "$HOME" ]不会对$HOME执行变量替换。如果你想要shell处理，那么要么使用shell形式，要么直接执行shell，例如：ENTRYPOINT [ "sh", "-c", "echo $HOME" ]。当使用exec形式并直接执行shell时，就像shell形式的情况一样，它是执行环境变量扩展的shell，而不是docker。

### Shell形式ENTRYPOINT示例

你可以为ENTRYPOINT指定一个纯字符串，它将在/bin/sh -c中执行。这种形式将使用shell处理来替换shell环境变量，并将忽略任何CMD或docker run命令行参数。要确保docker stop能正确发信号给任何长时间运行的ENTRYPOINT可执行文件，你需要记住用exec启动它：
```shell
FROM ubuntu
ENTRYPOINT exec top -b
```

运行此镜像时，你将看到单个PID 1进程：
```shell
$ docker run -it --rm --name test top
Mem: 1704520K used, 352148K free, 0K shrd, 0K buff, 140368121167873K cached
CPU:  5% usr  0% sys  0% nic  94% idle  0% io  0% irq  0% sirq
Load average: 0.08 0.03 0.05 2/98 6
 PID  PPID USER   STAT  VSZ %VSZ %CPU COMMAND
  1   0 root   R   3164  0%  0% top -b
```

在docker stop时干净地退出：
```shell
$ /usr/bin/time docker stop test
test
real	0m 0.20s
user	0m 0.02s
sys	0m 0.04s
```

如果你忘记将exec添加到ENTRYPOINT的开头：
```shell
FROM ubuntu
ENTRYPOINT top -b
CMD --ignored-param1
```

然后，你可以运行它（为了下一步给个名字）：
```shell
$ docker run -it --name test top --ignored-param2
Mem: 1704184K used, 352484K free, 0K shrd, 0K buff, 140621524238337K cached
CPU:  9% usr  2% sys  0% nic  88% idle  0% io  0% irq  0% sirq
Load average: 0.01 0.02 0.05 2/101 7
 PID  PPID USER   STAT  VSZ %VSZ %CPU COMMAND
  1   0 root   S   3168  0%  0% /bin/sh -c top -b cmd cmd2
  7   1 root   R   3164  0%  0% top -b
```
你可以从top的输出中看到指定的ENTRYPOINT不是PID 1。

如果运行docker stop test，容器将不会干净地退出 - stop命令将被强制在超时后发送SIGKILL：
```
$ docker exec -it test ps aux
PID  USER   COMMAND
  1 root   /bin/sh -c top -b cmd cmd2
  7 root   top -b
  8 root   ps aux
$ /usr/bin/time docker stop test
test
real	0m 10.19s
user	0m 0.04s
sys	0m 0.03s
```

### 了解CMD和ENTRYPOINT如何相互作用

CMD和ENTRYPOINT指令都定义了运行容器时执行的命令。 这里有点规则描述他们之间的合作。

· Dockerfile应至少指定一个CMD或ENTRYPOINT命令。

· 使用容器作为可执行文件时，应定义ENTRYPOINT。

· CMD应该用作为ENTRYPOINT命令定义默认参数或在容器中执行特定命令的方法。

· 使用备用参数运行容器时，将覆盖CMD。

下表显示了针对不同ENTRYPOINT / CMD组合执行的命令：

|                                      | ***\*No ENTRYPOINT\****    | ***\*ENTRYPOINT exec_entry p1_entry\**** | ***\*ENTRYPOINT [“exec_entry”, “p1_entry”]\**** |
| ------------------------------------ | -------------------------- | ---------------------------------------- | ----------------------------------------------- |
| ***\*No CMD\****                     | error, not allowed         | /bin/sh -c exec_entry p1_entry           | exec_entry p1_entry                             |
| ***\*CMD [“exec_cmd”, “p1_cmd”\****] | exec_cmd p1_cmd            | /bin/sh -c exec_entry p1_entry           | exec_entry p1_entry exec_cmd p1_cmd             |
| ***\*CMD [“p1_cmd”, “p2_cmd”]\****   | p1_cmd p2_cmd              | /bin/sh -c exec_entry p1_entry           | exec_entry p1_entry p1_cmd p2_cmd               |
| ***\*CMD exec_cmd p1_cmd\****        | /bin/sh -c exec_cmd p1_cmd | /bin/sh -c exec_entry p1_entry           | exec_entry p1_entry /bin/sh -c exec_cmd p1_cmd  |

## VOLUME 创建外部卷

VOLUME ["/data"]

VOLUME指令创建具有指定名称的安装点，并将其标记为从本机主机或其他容器保存外部安装的卷。 该值可以是JSON数组，VOLUME ["/var/log/"]或具有多个参数的纯字符串，例如VOLUME /var/log 或 VOLUME /var/log /var/db。 有关通过Docker客户端提供的更多信息/示例和安装说明，请参阅[通过卷共享目录](#/mount-a-host-directory-as-a-data-volume)文档。

docker run命令使用基础镜像中指定位置存在的任何数据初始化新创建的卷。 例如，请考虑以下Dockerfile片段：

```shell
FROM ubuntu
RUN mkdir /myvol
RUN echo "hello world" > /myvol/greeting
VOLUME /myvol
```

此Dockerfile会生成一个镜像，该镜像会导致docker run在/myvol上创建新的挂载点，并将greeting文件复制到新创建的卷中。

### 有关指定卷的说明

关于Dockerfile中的卷，请记住以下事项。

**·** ***\*基于Windows容器上的卷\****：使用基于Windows的容器时，容器中卷的目标必须是以下之一：

· 不存在或空目录

· C:以外的驱动器：

**·** ***\*从Dockerfile中更改卷\****：如果任何构建步骤在声明后更改卷内的数据，那么这些更改将被丢弃。

**·** ***\*JSON格式\****：列表被解析为JSON数组。你必须用双引号（"）而不是单引号（’）括起来。

**·** ***\*主机目录在容器运行时声明\****：主机目录（挂载点）本质上是依赖于主机的。这是为了保持镜像的可移植性，因为不能保证给定的主机目录在所有主机上都可用。因此，你无法从Dockerfile中挂载主机目录。 VOLUME指令不支持指定host-dir参数。你必须在创建或运行容器时指定挂载点。
```shell
## USER 设置当前用户
USER <user>[:<group>] or
USER <UID>[:<GID>]
```

USER指令设置用户名（或UID）以及可选的用户组（或GID），以便在运行镜像时以及Dockerfile中跟随它的任何RUN、CMD和ENTRYPOINT指令时使用。

警告：当用户没有主组时，将使用root组运行镜像（或下一条指令）。

在Windows上，如果用户不是内置帐户，则必须先创建用户。 这可以使用作为Dockerfile一部分调用的net user命令来完成。
```shell
  FROM microsoft/windowsservercore
  # Create Windows user in the container
  RUN net user /add patrick
  # Set it for subsequent commands
  USER patrick
```
## WORKDIR 设置工作目录
```shell
WORKDIR /path/to/workdir
```
WORKDIR指令为Dockerfile中的任何RUN、CMD、ENTRYPOINT、COPY和ADD指令设置工作目录。 如果WORKDIR不存在，即使它未在任何后续Dockerfile指令中使用，也将创建它。

WORKDIR指令可以在Dockerfile中多次使用。 如果提供了相对路径，则它将相对于先前WORKDIR指令的路径。 例如：
```shell
WORKDIR /a
WORKDIR b
WORKDIR c
RUN pwd
```

此Dockerfile中最终pwd命令的输出为/a/b/c。

WORKDIR指令可以解析先前使用ENV设置的环境变量。 你只能使用Dockerfile中显式设置的环境变量。 例如：
```shell
ENV DIRPATH /path
WORKDIR $DIRPATH/$DIRNAME
RUN pwd
```

此Dockerfile中最后一个pwd命令的输出将是/path/$DIRNAME。

## ARG 构建参数
* ARG <name>[=<default value>]

ARG指令使用--build-arg <varname>=<value>标志定义一个变量，用户可以使用docker build命令在构建时将该变量传递给构建器。如果用户指定了未在Dockerfile中定义的构建参数，则构建会输出警告。

[Warning] One or more build-args [foo] were not consumed.

Dockerfile可以包括一个或多个ARG指令。 例如，以下是有效的Dockerfile：
```shell
FROM busybox
ARG user1
ARG buildno
```

警告：建议不要使用构建时变量来传递密码，例如github密钥、用户凭据等。使用docker history命令，任何图像用户都可以看到构建时变量值。

### Default values 默认值

ARG指令可以包含可选的默认值：
```shell
FROM busybox
ARG user1=someuser
ARG buildno=1
```
如果ARG指令具有默认值，并且在构建时没有传递值，则构建器将使用默认值。

### Scope 作用域

ARG变量定义从Dockerfile中定义的行开始生效，而不是从命令行或其他地方的参数使用时才生效。 例如，考虑这个Dockerfile：
```shell
FROM busybox
USER ${user:-some_user}
ARG user
USER $user
```

用户通过调用以下内容构建此文件：
```shell
$ docker build --build-arg user=what_user .
````

第2行的USER设值为some_user，因为在后续第3行才定义了用户变量。第4行的USER在定义用户时设值为what_user，并在命令行上传递what_user值。 在通过ARG指令定义之前，对变量的任何使用都会导致空字符串。

ARG指令在构建阶段结束时超出作用域。 要在多个阶段中使用arg，每个阶段必须包含ARG指令。
```shell
FROM busybox
ARG SETTINGS
RUN ./run/setup $SETTINGS
FROM busybox
ARG SETTINGS
RUN ./run/other $SETTINGS
```
### Using ARG variables 使用参数变量

你可以使用ARG或ENV指令指定RUN指令可用的变量。 使用ENV指令定义的环境变量始终覆盖同名的ARG指令。 考虑这个带有ENV和ARG指令的Dockerfile。
```shell
FROM ubuntu
ARG CONT_IMG_VER
ENV CONT_IMG_VER v1.0.0
RUN echo $CONT_IMG_VER
```

然后，假设使用此命令构建此镜像：
```shell
$ docker build --build-arg CONT_IMG_VER=v2.0.1 .
```

在这种情况下，RUN指令使用v1.0.0而不是用户传递的ARG设置：v2.0.1。此行为类似于shell脚本，其中本地作用域的变量会覆盖作为参数传递或从定义点继承的环境变量， 。

使用上面的示例，但不同的ENV规范，你可以在ARG和ENV指令之间创建更有用的交互：
```shell
FROM ubuntu
ARG CONT_IMG_VER
ENV CONT_IMG_VER ${CONT_IMG_VER:-v1.0.0}
RUN echo $CONT_IMG_VER
```
与ARG指令不同，ENV值始终保留在构建的镜像中。 考虑没有--build-arg标志的docker构建：
```shell
$ docker build .
```
· 1

使用此Dockerfile示例，CONT_IMG_VER仍然保留在镜像中，但其值为v1.0.0，因为它是ENV指令在第3行中的默认设置。

此示例中的变量扩展技术允许你从命令行传递参数，并通过利用ENV指令将它们保存在最终镜像中。 [只有一组有限的Dockerfile指令](#Environment_replacement_198)支持变量扩展。

### Predefined ARGs 预定义的ARG

Docker有一组预定义的ARG变量，你可以在Dockerfile中使用而无需相应的ARG指令。

· HTTP_PROXY

· http_proxy

· HTTPS_PROXY

· https_proxy

· FTP_PROXY

· ftp_proxy

· NO_PROXY

· no_proxy

要使用它们，只需使用标志在命令行上传递它们：

--build-arg <varname>=<value>


默认情况下，这些预定义变量将从docker history的输出中排除。 排除它们可降低在HTTP_PROXY变量中意外泄露敏感验证信息的风险。

例如，考虑使用--build-arg HTTP_PROXY=http://user:pass@proxy.lon.example.com构建以下Dockerfile
```shell
FROM ubuntu
RUN echo "Hello World"
```

在这种情况下，HTTP_PROXY变量的值在docker history中不可用，并且不会被缓存。 如果要更改位置，并且代理服务器已更改为http://user:pass@proxy.sfo.example.com，则后续构建不会导致缓存未命中。

如果你需要覆盖此行为，则可以通过在Dockerfile中添加ARG语句来执行此操作，如下所示：
```shell
FROM ubuntu
ARG HTTP_PROXY
RUN echo "Hello World"
```

构建此Dockerfile时，HTTP_PROXY将保留在docker history中，并且更改其值会使构建缓存无效。

### Inpact on build caching 对构建缓存的影响

ARG变量不会像ENV变量那样持久存储到构建的镜像中。 但是，ARG变量会以类似的方式影响构建缓存。 如果Dockerfile定义了一个值与先前版本不同的ARG变量，那么在第一次使用时会发生“缓存未命中”，而不是其定义。 特别是，ARG指令之后的所有RUN指令都隐式使用ARG变量（作为环境变量），因此可能导致缓存未命中。 除非Dockerfile中存在匹配的ARG语句，否则所有预定义的ARG变量都将免于缓存。

例如，考虑这两个Dockerfile：
```shell
FROM ubuntu
ARG CONT_IMG_VER
RUN echo $CONT_IMG_VER
```
```shell
FROM ubuntu
ARG CONT_IMG_VER
RUN echo hello
```

如果在命令行上指定--build-arg CONT_IMG_VER=<value>，则在这两种情况下，第2行上的规范不会导致高速缓存未命中; 第3行确实导致缓存未命中。ARG CONT_IMG_VER导致RUN行被识别为与运行CONT_IMG_VER=<value> echo hello相同，因此如果<value>发生更改，我们将获得缓存未命中。

考虑同一命令行下的另一个示例：
```
FROM ubuntu
ARG CONT_IMG_VER
ENV CONT_IMG_VER $CONT_IMG_VER
RUN echo $CONT_IMG_VER
```

在此示例中，缓存未命中发生在第3行。由于ENV中的变量值引用ARG变量并且该变量通过命令行发生更改，因此发生未命中。 在此示例中，ENV命令使镜像包含该值。

如果ENV指令覆盖了同名的ARG指令，就像这个Dockerfile：
```shell
FROM ubuntu
ARG CONT_IMG_VER
ENV CONT_IMG_VER hello
RUN echo $CONT_IMG_VER
```

第3行不会导致缓存未命中，因为CONT_IMG_VER的值是常量（hello）。 因此，RUN（第4行）上使用的环境变量和值在构建之间不会发生变化。

## ONBUILD 为下游镜像添加触发器

ONBUILD [INSTRUCTION]

当镜像用作另一个构建的基础时，ONBUILD指令向镜像添加将在稍后执行的**触发**指令。触发器将在下游构建的上下文中执行，就好像它已经在下游Dockerfile中的FROM指令之后立即插入一样。

任何构建指令都可以注册为触发器。

如果要构建将用作构建其他镜像的基础镜像（例如，可以使用特定于用户的配置自定义的应用程序构建环境变量或守护进程），这将非常有用。

例如，如果你的镜像是可重用的Python应用程序构建器，则需要将应用程序源代码添加到特定目录中，并且可能需要在此之后调用构建脚本。你现在不能只调用ADD和RUN，因为你还无法访问应用程序源代码，并且每个应用程序构建都会有所不同。你可以简单地为应用程序开发人员提供一个样板Dockerfile来复制粘贴到他们的应用程序中，但这样做效率低，容易出错且难以更新，因为它与特定于应用程序的代码混合在一起。

解决方案是使用ONBUILD来注册预先指令，以便在下一个构建阶段运行。

以下是它的工作原理：

1. 当遇到ONBUILD指令时，构建器会向正在构建的图像的元数据添加触发器。该指令不会影响当前构建。

2. 在构建结束时，所有触发器的列表都存储在镜像清单中的OnBuild键下。可以使用docker inspect命令检查它们。

3. 稍后，可以使用FROM指令将镜像用作新构建的基础。作为处理FROM指令的一部分，下游构建器查找ONBUILD触发器，并按照它们注册的顺序执行它们。如果任何触发器失败，则中止FROM指令，这反过来导致构建失败。如果所有触发器都成功，则FROM指令完成，并且构建继续照常进行。

4. 执行后，触发器将从最终镜像中清除。换句话说，它们不是由“大孩子(grand-children)”构建继承的。

例如，你可以添加以下内容：

[...]

ONBUILD ADD . /app/src

ONBUILD RUN /usr/local/bin/python-build --dir /app/src

[...]


警告：不允许使用ONBUILD ONBUILD这样的链状ONBUILD指令。

警告：ONBUILD指令可能不会触发FROM或MAINTAINER指令。

## ***\*STOPSIGNAL 容器退出信号\****

STOPSIGNAL signal


STOPSIGNAL指令设置将发送给容器退出的系统调用信号。此信号可以是与内核的系统调用表中的位置匹配的有效无符号数，例如9，或SIGNAME格式的信号名，例如SIGKILL。

## HEALTHCHECK 健康检查

HEALTHCHECK指令有两种形式：

· HEALTHCHECK [OPTIONS] CMD command（通过在容器内运行命令来检查容器运行状况）

· HEALTHCHECK NONE（禁用从基础镜像继承的任何健康检查）

HEALTHCHECK指令告诉Docker如何测试容器以检查它是否仍在工作。可以检测到陷入无限循环且无法处理新连接的Web服务器，即使服务器进程仍在运行等情况。

当容器指定了运行状况检查时，除了**正常状态**外，它还具有运行状况。此状态最初为starting。每当健康检查通过时，它就会变得healthy（无论以前处于什么状态）。经过一定数量的连续失败后，它变得unhealthy。

可以在CMD之前出现的选项是：

· --interval=DURATION (默认值：30s)

· --timeout=DURATION (默认值：30s)

· --start-period=DURATION (默认值：0s)

· --retries=N (默认值：3)

运行状况检查将首先在容器启动后的**间隔(interval)***\*秒运行，然后在每次上一次检查完成后再\****间隔(interval)**秒。

如果单次运行的检查花费的时间超过**超时(timeout)**秒数，那么检查将被视为unhealthy。

它需要**重试(retries)**连续的健康检查失败才能将容器视为不健康。

**启动期间(start period)**为需要时间引导的容器提供初始化时间。在此期间探测失败将不计入最大重试次数。但是，如果在启动期间运行状况检查成功，则会将容器视为已启动，并且所有连续失败将计入最大重试次数。

Dockerfile中只能有一个HEALTHCHECK指令。如果列出多个，那么只有最后一个HEALTHCHECK才会生效。

CMD关键字之后的命令可以是shell命令（例如HEALTHCHECK CMD /bin/check-running）或exec数组（与其他Dockerfile命令一样;有关详细信息，请参阅例如ENTRYPOINT）。

命令的退出状态指示容器的运行状况。可能的值是：

· 0: success 成功 - 容器健康且随时可用

· 1: unhealthy 不健康 - 容器无法正常工作

· 2: reserved 保留 - 不要使用此退出代码
例如，要检查每五分钟左右网络服务器能够在三秒钟内提供网站的主页面：

HEALTHCHECK --interval=5m --timeout=3s \

 CMD curl -f http://localhost/ || exit 1

为了帮助调试失败的探测器，命令在stdout或stderr上写入的任何输出文本（UTF-8编码）都将存储在运行状况(health status)中，并可以使用docker inspect进行查询。 此类输出应保持较短（目前仅存储前4096个字节）。

当容器的运行状况更改时，将生成具有新状态的health_status事件。

在Docker 1.12中添加了HEALTHCHECK功能。

## SHELL
SHELL ["executable", "parameters"]

SHELL指令允许覆盖用于shell命令形式的默认shell。 Linux上的默认shell是["/bin/sh", "-c"]，在Windows上是["cmd", "/S", "/C"]。 SHELL指令必须以JSON格式写入Dockerfile。

SHELL指令在Windows上特别有用，其中有两个常用且相当不同的本机shell：cmd和powershell，以及包括sh的备用shell。

SHELL指令可以多次出现。 每个SHELL指令都会覆盖所有先前的SHELL指令，并影响所有后续指令。 例如：
```shell
FROM microsoft/windowsservercore
# Executed as cmd /S /C echo default
RUN echo default
# Executed as cmd /S /C powershell -command Write-Host default
RUN powershell -command Write-Host default
# Executed as powershell -command Write-Host hello
SHELL ["powershell", "-command"]
RUN Write-Host hello
# Executed as cmd /S /C echo hello
SHELL ["cmd", "/S", "/C"]
RUN echo hello
```
当在Dockerfile中使用它们的shell形式时，SHELL指令可能会影响以下指令：RUN，CMD和ENTRYPOINT。

以下示例是在Windows上找到的常见模式，可以使用SHELL指令简化：
```shell
RUN powershell -command Execute-MyCmdlet -param1 "c:\foo.txt"
```

docker调用的命令将是：
```shell
cmd /S /C powershell -command Execute-MyCmdlet -param1 "c:\foo.txt"
```

由于两个原因，这是低效的。 首先，调用一个不必要的cmd.exe命令处理器（也就是shell）。 其次，shell形式的每个RUN指令都需要额外的powershell -command前缀命令。

为了提高效率，可以采用两种机制中的一种。 一种是使用RUN命令的JSON形式，例如：
```shell
RUN ["powershell", "-command", "Execute-MyCmdlet", "-param1 \"c:\\foo.txt\""]
```

虽然JSON形式是明确的，并且不使用不必要的cmd.exe，但它需要通过双引号和转义使得更加赘言。 替代机制是使用SHELL指令和shell形式，为Windows用户提供更自然的语法，特别是与escape解析器指令结合使用时：
```shell
# escape=`
FROM microsoft/nanoserver
SHELL ["powershell","-command"]
RUN New-Item -ItemType Directory C:\Example
ADD Execute-MyCmdlet.ps1 c:\example\
RUN c:\example\Execute-MyCmdlet -sample 'hello world'
```
结果是:
```shell
PS E:\docker\build\shell> docker build -t shell .
Sending build context to Docker daemon 4.096 kB
Step 1/5 : FROM microsoft/nanoserver
 ---> 22738ff49c6d
Step 2/5 : SHELL powershell -command
 ---> Running in 6fcdb6855ae2
 ---> 6331462d4300
Removing intermediate container 6fcdb6855ae2
Step 3/5 : RUN New-Item -ItemType Directory C:\Example
 ---> Running in d0eef8386e97
  Directory: C:\
Mode         LastWriteTime     Length Name
----         -------------     ------ ----
d-----    10/28/2016  11:26 AM         Example
 ---> 3f2fbf1395d9
Removing intermediate container d0eef8386e97
Step 4/5 : ADD Execute-MyCmdlet.ps1 c:\example\
 ---> a955b2621c31
Removing intermediate container b825593d39fc
Step 5/5 : RUN c:\example\Execute-MyCmdlet 'hello world'
 ---> Running in be6d8e63fe75
hello world
 ---> 8e559e9bf424
Removing intermediate container be6d8e63fe75
Successfully built 8e559e9bf424
PS E:\docker\build\shell>
```

SHELL指令也可用于修改shell的运行方式。 例如，在Windows上使用SHELL cmd /S /C /V:ON|OFF，可以修改延迟的环境变量扩展语义。

如果需要备用shell，例如zsh、csh、tcsh等，也可以在Linux上使用SHELL指令。

在Docker 1.12中添加了SHELL功能。

## Dockerfile示例

下面你可以看到Dockerfile语法的一些示例。 如果你对更现实的东西感兴趣，请查看[Dockerization示例](https://docs.docker.com/engine/examples/)列表。
```shell
# Nginx
#
# VERSION        0.0.1
FROM    ubuntu
LABEL Description="This image is used to start the foobar executable" Vendor="ACME Products" Version="1.0"
RUN apt-get update && apt-get install -y inotify-tools nginx apache2 openssh-server
```

```shell
# Firefox over VNC
#
# VERSION        0.3
FROM ubuntu
# Install vnc, xvfb in order to create a 'fake' display and firefox
RUN apt-get update && apt-get install -y x11vnc xvfb firefox
RUN mkdir ~/.vnc
# Setup a password
RUN x11vnc -storepasswd 1234 ~/.vnc/passwd
# Autostart firefox (might not be the best way, but it does the trick)
RUN bash -c 'echo "firefox" >> /.bashrc'
EXPOSE 5900
CMD   ["x11vnc", "-forever", "-usepw", "-create"]
```

```shell
# Multiple images example
#
# VERSION        0.1
FROM ubuntu
RUN echo foo > bar
# Will output something like ===> 907ad6c2736f
 
FROM ubuntu
RUN echo moo > oink
# Will output something like ===> 695d7793cbe4
# You'll now have two images, 907ad6c2736f with /bar, and 695d7793cbe4 with
# /oink.
```

 