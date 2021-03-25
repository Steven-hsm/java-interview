<center><h1>git仓库代码迁移</h1></center>

#### 1. 拉取原仓库git代码

```shell
git clone 原仓库地址url
```

#### 2. 拉取原仓库所有的分支到本地

```shell
git branch -r | grep -v '\->' | while read remote; do git branch --track "${remote#origin/}" "$remote"; done
```

```shell
git fetch 
git pull
```



#### 3. 修改`.git`目录下的`config`文件

```shell
[core]
	repositoryformatversion = 0
	filemode = false
	bare = false
	logallrefupdates = true
	symlinks = false
	ignorecase = true
[remote "origin"]
	url = 你的远程地址  #修改这里的地址（实际文件里不要加上这条注释）
	fetch = +refs/heads/*:refs/remotes/origin/*
```

#### 4. 推送master分支（要求输入密码）

```shell
git push -u origin master
```

#### 5.推送所有分支

```shell
git push --all
```
