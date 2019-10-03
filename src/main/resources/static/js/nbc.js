document.getElementById("select_hospital").onchange = (function(){
    $('.select_hospital').hide();
    var select_hospital = $(this).val();

    switch (select_hospital) {
        case "hospital":
            $('#' + select_hospital).show();
            document.getElementById("newborncertificate").setAttribute("action", "/new");
            break;

        case "null_hospital":
            $('#' + select_hospital).show();
            document.getElementById("newborncertificate").setAttribute("action", "/see");
            break;
    }
});

document.getElementById("idpp").onchange = (function($){

    /*

        Copyright  (c) opoloo
        https://github.com/opoloo/jquery_upload_preview/blob/master/assets/js/jquery.uploadPreview.js

    */
    if (window.File && window.FileList && window.FileReader) {
        var files = this.files;

        var file = files[0];

        var reader = new FileReader();
/*

        reader.onload = (function(file) {
            return function(ev) {
                // Here you can use `e.target.result` or `this.result`
                // and `f.name`.
            };
        })(file);

        reader.readAsText(file);
*/
    }

    else {
        alert("You need a browser with file reader support, to use this form properly.");
        return false;
    }

    /*
        var reader = new FileReader();

        reader.onload = function (ev) {

        }

        $('#file').show();
        document.getElementById("file").setAttribute("src", $(this).val());

        alert($(this).attr("value"));

    */

});
