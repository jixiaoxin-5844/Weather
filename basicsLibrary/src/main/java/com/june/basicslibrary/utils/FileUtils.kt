package com.june.basicslibrary.utils

import java.io.File


/**
 * author : Hyt
 * time : 2020/09/08
 * version : 1.0
 *
 */
class FileUtils {
    companion object {

        /**
         * 清空路径名的指定名的文件
         *  @param filePath 文件绝对路径
         * */
        fun clearFile(filePath: String): Boolean {
            val file = File(filePath)
            if (file.isFile && file.exists()) {
                L.d("info", "已删除文件${file.name},文件路径:" + file.path)
                return file.delete()
            }
            return false
        }

        /**
         *   检测某文件是否存在
         *   @param filePath 绝对路径
         * */
        fun checkFileExist(filePath : String):Boolean{
            return File(filePath).exists()
        }


        /**
         * 删除文件夹以及目录下的文件
         * @param   filePath 被删除目录的文件路径
         * @return  目录删除成功返回true，否则返回false
         */
        /*  fun deleteDirectory(filePath: String): Boolean {
              var filePath = filePath
              var flag = false
              //如果filePath不以文件分隔符结尾，自动添加文件分隔符
              if (!filePath.endsWith(File.separator)) {
                  filePath += File.separator
              }
              val dirFile = File(filePath)
              if (!dirFile.exists() || !dirFile.isDirectory) {
                  return false
              }
              flag = true
              val files = dirFile.listFiles()
              //遍历删除文件夹下的所有文件(包括子目录)
              for (i in files.indices) {
                  if (files[i].isFile) {
                      //删除子文件
                      flag = deleteFile(files[i].absolutePath)
                      if (!flag) break
                  } else {
                      //删除子目录
                      flag = deleteDirectory(files[i].absolutePath)
                      if (!flag) break
                  }
              }
              return if (!flag) false else dirFile.delete()
              //删除当前空目录
          }*/

    }
}