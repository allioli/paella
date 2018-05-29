
var utils = function () {

    utils.prototype.generateTestEmail = function () {
        return 'test_user' + Math.floor(Math.random() * 1000) + '@test.com';
    };

    utils.prototype.generatePassword = function () {
        return Math.random().toString(18).substring(2,10)
    };

};

module.exports = new utils();