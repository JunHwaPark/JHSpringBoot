var user = {
    init : function () {
        var _this = this;
        $('#btn-signup').on('click', function() {
            _this.signup();
        })
        $('#btn-login').on('click', function () {
            _this.login();
        })
        $('#btn-delete').on('click', function () {
            _this.delete();
        })
    },
    signup : function () {
        if ($('#password').val() != $('#password2').val()) {
            alert('재입력한 비밀번호가 다릅니다.');
            return;
        }
        var data = {
            id: $('#id').val(),
            password: $('#password').val(),
            name: $('#name').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/user',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('회원가입되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    login : function () {
        var data = {
            username: $('#username').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/login',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            //alert("수정되었습니다.");
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }/*,
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/location/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }*/
};

user.init();