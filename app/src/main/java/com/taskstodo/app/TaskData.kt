package com.taskstodo.app

import org.mongodb.kbson.ObjectId

class TaskData(
    var _id: ObjectId,
    var name: String,
    var date: String,
    var isClosed: Boolean,
        )