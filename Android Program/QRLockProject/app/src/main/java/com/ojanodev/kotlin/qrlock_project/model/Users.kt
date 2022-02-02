package com.ojanodev.kotlin.qrlock_project.model

data class Users(val uid:String,
                 val nama:String,
                 val default:String,
                 val qr1:Boolean ?= false,
                 val qr2:Boolean ?= false,
                 val qr3:Boolean ?= false,
                 val qr4:Boolean ?= false,
                 val qr5:Boolean ?= false,
                 val qr6:Boolean ?= false,
                 val qr7:Boolean ?= false,
                 val qr8:Boolean ?= false,
                 val qr9:Boolean ?= false,
                 val qr10:Boolean ?= false)