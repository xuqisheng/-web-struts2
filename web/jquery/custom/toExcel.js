//<script src="//ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
// <script src="//cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"></script>

//demo
(function(){
    $("#inTable").table2excel({
        exclude: ".excludeThisClass",
        name: "Worksheet Name",
        exclude_inputs: false,
        fileext: ".xls",
        filename: $("#tableName").text()
    });
});