$('select').select2();

var reader = new FileReader();
reader.onload = function (e) {
    var $image_view = $('.file-upload .image-view');
    var $image = $('<img />');

    $image.attr('src', e.target.result);
    $image_view.empty();
    $image_view.append($image);

    $('.file-upload-cover').hide();
    $('.setting .msg').hide();
};

$('.file-upload-cover, .image-view').click(function (e) {
    $('#file-image').click();
    e.preventDefault();
});

$('#file-image').change(function (event) {
    handleFile(event.target.files);
});

$('.js-toggle').click(function() {
    var $this = $(this);
    var isOn = $this.hasClass('fa-toggle-on');
    if(isOn) {
        $this.removeClass('fa-toggle-on');
        $this.addClass('fa-toggle-off')
        $this.siblings('input').val(0);
    } else {
        $this.addClass('fa-toggle-on');
        $this.removeClass('fa-toggle-off')
        $this.siblings('input').val(1);
    }
});

var dropbox = $('.file-upload-cover').get(0);
dropbox.addEventListener("dragenter", dragenter, false);
dropbox.addEventListener("dragover", dragover, false);
dropbox.addEventListener("drop", drop, false);
function dragenter(e) {
    e.stopPropagation();
    e.preventDefault();
}

function dragover(e) {
    e.stopPropagation();
    e.preventDefault();
}
function drop(e) {
    e.stopPropagation();
    e.preventDefault();

    var files = e.dataTransfer.files;

    $('#file-image').get(0).files = files;

    handleFile(files);
}

var handleFile = function (files) {
    var f = files[0];
    if (!/image\/\w+/.test(f.type)) {
        showMessage("请确保文件为图像类型");
        return false;
    }
    reader.readAsDataURL(f);
};

var showMessage = function (message) {
    var $msg = $('.setting .msg');
    if ($msg.length > 0) {
        $msg.find('span').html(message);
    } else {
        $msg = $('<div class="msg"><i class="fa fa-warning"></i><span></span></div>');
        $msg.find('span').html(message);
        $('.setting').prepend($msg);
    }
};