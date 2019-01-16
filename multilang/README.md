多语言处理，多语言数据以数据库字段为最小维度，如有字段游戏名（app.app_name），该字段中以 json 的方式存放多语言数据：
```json
{"defaulz":{"lang":"en_US","value":{"content":"game-a","status":1}},"others":[{"lang":"zh_CN","value":{"content":"游戏 a","status":1}},{"lang":"zh_TW","value":{"content":"游戏 A","status":1}}]}
```

封装了对象实体与 json 字符之间的转换，前端接收多语言数据，前端请求指定语言数据的操作。