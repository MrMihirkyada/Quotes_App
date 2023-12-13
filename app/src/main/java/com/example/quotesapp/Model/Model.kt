package com.example.quotesapp.Model

class Model(var id: Int, var shayari: String,var category_id: Int,var status : Int) {

}

class categoryModel(var id: Int, var category: String)
{

}

class DisplayCategoryModel(var shyari_id: Int, var shyari_item: String, var category_id: Int,var status : Int)
{

}
class favoratshayri(var Shayari_id : Int,var Shayari_item : String,var fav : Int)
{

}