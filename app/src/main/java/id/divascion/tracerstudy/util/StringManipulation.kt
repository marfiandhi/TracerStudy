package id.divascion.tracerstudy.util

class StringManipulation {

    fun money(x: Int): String {
        var text = ""
        val tmp = x.toString().toCharArray()
        var dmy = tmp.size%3
        if(dmy==0) {
            dmy = 3
        }
        for(i in tmp.indices) {
            text += tmp[i]
            if(i+1==dmy && i+1 < tmp.size) {
                text += "."
                dmy += 3
            }
        }
        return text
    }

    fun thumbnailImage(url: String): String {
        var newUrl = ""
        val oldUrl = url.toCharArray()
        var extension = ""
        for (i in oldUrl.size-1 downTo 0) {
            extension = oldUrl[i] + extension
            if(oldUrl[i]=='.') {
                for(j in oldUrl.indices) {
                    if(j<i) {
                        newUrl += oldUrl[j]
                    } else {
                        newUrl += "-150x150$extension"
                        break
                    }
                }
                break
            }
        }
        return newUrl
    }

    fun mediumThumbnailImage(url: String): String {
        var newUrl = ""
        val oldUrl = url.toCharArray()
        var extension = ""
        for (i in oldUrl.size-1 downTo 0) {
            extension = oldUrl[i] + extension
            if(oldUrl[i]=='.') {
                for(j in oldUrl.indices) {
                    if(j<i) {
                        newUrl += oldUrl[j]
                    } else {
                        newUrl += "-300x300$extension"
                        break
                    }
                }
                break
            }
        }
        return newUrl
    }

    fun listExtracted(list: String): String {
        var extractString = ""
        var before = ""
        var listIterator = false
        var listSpace = false
        for(i in list.indices) {
            if(listIterator && listSpace) {
                extractString += list[i]
            } else if(before.equals("A", true) || before.equals("B", true)
                || before.equals("C", true) || before.equals("D", true)
                || before.equals("E", true) || before.equals("F", true)
                || before.equals("G", true) || before.equals("H", true)
                || before.equals("I", true) || before.equals("J", true)
                || before.equals("K", true) || before.equals("L", true)
                || before.equals("M", true) || before.equals("N", true)
                || before.equals("O", true) || before.equals("P", true)
                || before.equals("Q", true) || before.equals("R", true)
                || before.equals("S", true) || before.equals("T", true)
                || before.equals("U", true) || before.equals("V", true)
                || before.equals("W", true) || before.equals("X", true)
                || before.equals("Y", true) || before.equals("Z", true)
                && list[i]=='.' && !listIterator) {
                listIterator = true
            } else if(listIterator && !listSpace && list[i]==' ') {
                listSpace = true
            }
            before = list[i].toString()
        }
        return extractString
    }
}