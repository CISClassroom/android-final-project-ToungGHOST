package th.ac.kku.cis.mobileapp.myapplication

class posts{
    companion object Factory {
        fun create(): posts = posts()
    }
    var namep: String? = null
    var objectId: String? = null
    var listp: String? = null
    var datep: String? = null
    var userp:String?=null
}

class comment{
    companion object Factory {
        fun create(): comment = comment()
    }
    var commentp: String? = null
    var commentId: String? = null
    var objectIdP: String? = null
}