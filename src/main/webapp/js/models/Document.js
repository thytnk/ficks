var app = app || {};
app.Document = Backbone.Model.extend({
//    urlRoot: "/document",

//    idAttribute: "_id",

    defaults: {
        'id':'',
        /** 資料別 */
        'category':'',
        /** 種類 */
        'purpose':'',
        /** 分野 */
        'area':'',
        /** 成否 */
        'result':'',
        /** 成否要因 */
        'reason':'',
        /** 部署名 */
        'deptName':'',
        /** 従業員番号 */
        'empNumber':'',
        /** 担当者名 */
        'authorName':'',
        /** コメント */
        'description': '',
        /** 顧客名 */
        'customerName':'',
        'resourcename': ''
    },

    initialize: function() {
        //console.log(JSON.stringify(this));
    }
});
