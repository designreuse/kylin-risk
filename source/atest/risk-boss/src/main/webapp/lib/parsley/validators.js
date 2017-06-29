(function(){
    window.ParsleyConfig = window.ParsleyConfig || {};
    window.ParsleyConfig.validators = window.ParsleyConfig.validators || {};

    window.ParsleyValidator.addValidator('dateiso',
        function (value) {
            return /^(\d{4})\D?(0[1-9]|1[0-2])\D?([12]\d|0[1-9]|3[01])$/.test(value);
        },256);



    var parseRequirement = function (requirement) {
        if (isNaN(+requirement))
            return parseFloat($(requirement).val());
        else
            return +requirement;
    };

// Greater than validator
    window.ParsleyValidator.addValidator('gt',
        function (value, requirement) {
            return parseFloat(value) > parseRequirement(requirement);
        },32
    );


// Greater than or equal to validator
    window.ParsleyValidator.addValidator('gte',
        function (value, requirement) {
            return parseFloat(value) >= parseRequirement(requirement);
        },32
    );


// Less than validator
    window.ParsleyValidator.addValidator('lt',
        function (value, requirement) {
            return parseFloat(value) < parseRequirement(requirement);
        },32
    );

// Less than or equal to validator
    window.ParsleyValidator.addValidator('lte',
        function (value, requirement) {
            return parseFloat(value) <= parseRequirement(requirement);
        },32
    );

// Not equal to validator
    window.ParsleyValidator.addValidator('notequalto',
        function (value, requirement) {
            return value !== ($(requirement).length ? $(requirement).val() : requirement);
        },256
    );

    /**
     * 手机号验证
     */
    window.ParsleyValidator.addValidator(
        'mobile',
        function (value) {
            var exp = /^1[345789][0-9]{9}$/;
            return value&&exp.test(value);
        }, 30);
    /**
     * 邮编验证
     */
    window.ParsleyValidator.addValidator(
        'zipcode',
        function (value) {
            var exp = /^[1-9][0-9]{5}$/;
            return value&&exp.test(value);
        }, 30);
    /**
     * 小数位数验证
     */
    window.ParsleyValidator.addValidator(
        'numberdigit',
        function (value,digit) {
            if(digit==null||digit=='undefined'){
                return false;
            }
            if(typeof digit !='number'){
                return false;
            }
            var reg='^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)?(?:\\.\\d{1,'+digit+'})?$'
            var numberReg = new RegExp(reg);
            return value&&(numberReg.test(value));
        }, 30);
})();
