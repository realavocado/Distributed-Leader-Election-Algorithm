--2月19日凌晨版：每个子环完成LCR后，记下自身进行的round数，然后把round赋给main ring的interface
的awakeRound。主环在模拟模型中是和子环同时开始，然后等到子环选出leader的轮数到达之后，interface开始
苏醒awake，并且会在界面提示出interface在这一轮wake up了。直到选举完成。
下一步想完善的是：使interface在wake up之前，显示出的uiquedID sendID等等全是-1.

--2月20凌晨更新：每个interface processor在醒来之前，所显示的信息全都是-1.醒来之后才会被附上它对应
的sub-ring的leader的unique ID。 完成了上一版想要完善的点。

--2月25日晚更新：可以给processor的ID进行升序和降序的分配