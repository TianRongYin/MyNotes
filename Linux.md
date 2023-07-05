# Git

Git 有四个区域:

- 3个本地区域
  - 工作区(Workspace): 存放项目代码的地方。
  - 暂存区(Stage): 存放临时的改动, 事实上它只是一个文件, 保存即将提交的文件列表信息。
  - 资源库(Repository): 安全存放数据的位置, 这里面有提交到所有版本的数据。其中 HEAD 指向最新放入仓库的版本。
- 1个远程区域
  - 远程库(Remote): 托管代码的服务器。

HEAD目录下最新版本号

Git常用命令：

- `Git Bash Here`打开命令行
- `git init`在当前目录创建仓库
- `git clone https：（路径） 重命名`从外部从库下载
- `git config user.name 用户名` `git config user.email 邮箱`全局配置
- `git config --global user.name 用户名`在c盘用户目录下的.gitconfig下找到全局配置文件
- `git status`查看暂存区状态
- `git add 文件名称`将工作区中被追踪的文件放入暂存区，`git add *.txt`扩展名为txt的文件全部放入暂存区
- `git rm --cached  文件名称`放回工作区
- `git commit -m 消息描述`将暂存区的文件提交到存储区
- `git log (版本号)(--oneline)`（该版本之前的）提交历史记录，oneline是简化，==版本号可以用标签替代==

- `git restore 文件名`工作区误删除之后修复
- `git reset --hard 版本号`重置版本，该版本之后的提交记录会丢失
- `git revert 版本号` 重新提交，回到该版本，这样不会丢失提交记录
- `git branch 分支名`创建新的分支，前提是这个仓库有提交文件
- `git branch -v`显示所有分支
- `git checkout 分支名`切换分支
- `git checkout -b 分支名`创建并切换分支
- `git branch -d 分支名`删除分支
- `git merge 分支名`需要在主分支上完成，合并其他分支，有异常则会停止等待我们处理（MERGING）：add 和commit
- `git tag`所有标签
- `git tag 标签名 版本号`创建标签
- `git tag -d 标签名`删除标签

- `git checkout -b <分支的名称> <tag的名称>`
- 远程仓库克隆下来的仓库config配置文件中有`remote "名称"`代替url
- `git remote add/rename/remove 名称 url`
- `git push/pull 名称`

