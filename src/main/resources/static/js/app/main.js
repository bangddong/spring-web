let main = {
    init: function () {
        let _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save: function () {
        let data = {
            title: $('#postTitle').val(),
            author: $('#postAuthor').val(),
            content: $('#summernote').val()
        };

        $.ajax({
            type: 'POST',
            url: '/post',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(error);
        });
    },
    delete: function () {
        let url = '/post/' + $('#postId').val();
        $.ajax({
            type: 'DELETE',
            url: url
        }).done(function () {
            alert('글이 삭제되었습니다..');
            location.href= "/index";
        }).fail(function (error) {
            alert(error);
        });
    },
    post: function () {
        let url = '/post/commnet';
        $.ajax({
            type: 'POST',
            url: '/post/comment',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('댓글이 등록되었습니다.');
            location.href= "/index";
        }).fail(function (error) {
            alert(error);
        });
    },


};

main.init();