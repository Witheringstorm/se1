'use strict';

angular.module("docs").controller("My_register", function(Restangular, $scope, $rootScope, $state, $stateParams, $dialog, User, $translate) {
    // 初始化注册数据模型
    $scope.registerData = {
        username: '',
        password: '',
        confirmPassword: '',
        email: '', // 添加 email 字段
        storageQuota: '' // 添加 storageQuota 字段
    };

    // 注册方法
    $scope.Register = function() {
        console.log('Register button clicked');
        // 验证表单是否有效
        if ($scope.registerForm.$invalid) {
            // 显示表单无效的提示
            showMessageBox(
                $translate.instant('register.form_invalid_title'),
                $translate.instant('register.form_invalid_message'),
                [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}]
            );
            return;
        }

        // 验证密码是否一致
        if ($scope.registerData.password !== $scope.registerData.confirmPassword) {
            // 显示密码不一致的提示
            showMessageBox(
                $translate.instant('register.password_mismatch_title'),
                $translate.instant('register.password_mismatch_message'),
                [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}]
            );
            return;
        }

        // 发送注册请求到后端
        Restangular.one('user').customPUT({
            username: $scope.registerData.username,
            password: $scope.registerData.password,
            email: $scope.registerData.email,
            storage_quota: $scope.registerData.storageQuota
        }).then(function() {
            // 注册成功
            showMessageBox(
                $translate.instant('register.success_title'),
                $translate.instant('register.success_message'),
                [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}]
            ).then(function() {
                // 注册成功后跳转到登录页面
                $state.go('login');
            });
        }, function(response) {
            // 注册失败
            showMessageBox(
                $translate.instant('register.error_title'),
                $translate.instant('register.error_message', {error: response.data.message}),
                [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}]
            );
        });
    };

    // 显示消息框的辅助方法
    function showMessageBox(title, message, buttons) {
        return $dialog.messageBox(title, message, buttons);
    }
});