$(document).ready(function() {
    $('.custom-file-input').on('change', function() {
        realVal = $(this).val();
        lastIndex = realVal.lastIndexOf('\\') + 1;
        if(lastIndex !== -1) {
            realVal = realVal.substr(lastIndex);
            $(this).prev('.mask').find('.fileInputText').val(realVal);
        }
    });
});