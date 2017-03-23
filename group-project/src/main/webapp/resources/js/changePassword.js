/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var converter = new Showdown.converter();

var PasswordCard = React.createClass({displayName: "PasswordCard",
    
    componentDidMount: function componentDidMount(){
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideModal);
        
    },
    
    handleSubmit: function (e) {
        e.preventDefault();
        var newPassword = this.refs.newPassword.getDOMNode().value.trim();
         $.ajax({
            url: "/changePasswordAction",
            dataType: 'json',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: newPassword,
            success: function (data) {
//                console.log(data)
                if(data.errors == null) {
                    location = '/home';
                }else
                {
                    alert("Password couldn't been change");
                }
            },
            error: function (xhr, status, err) {
                console.error(status, err.toString());
            }
        });
        return
    },
    render: function () {
        return (React.createElement('div',{ className: 'modal fade', id: "changePswdModal" },
                    React.createElement('div',{ className: 'modal-dialog' },
                        React.createElement('div',{ className: 'modal-content' },
                            React.createElement('div',{ className: 'modal-header' },
                                React.createElement('button',{ type: 'button', className: 'close', 'aria-label': 'Close',
                                'data-dismiss': 'modal'},
                                    React.createElement('span',{ 'aria-hidden': 'true' },'\xD7')),
                            React.createElement('h3',{ className: 'modal-title' },'Change password')),
                            React.createElement('div',{ className: 'modal-body' },
                                React.createElement("label", {type: "text", htmlFor: "password"},
                                    React.createElement('span',{ className: "glyphicon glyphicon-lock" }), " Enter new password: "),
                                React.createElement("input", {className:'form-control input-sm', type: "password", placeholder: "New password", id: "newPassword", ref: "newPassword"})),
                            React.createElement('div',{ className: 'modal-footer' },
                                React.createElement('button',{ type: 'button', className: 'btn btn-primary', onClick: this.handleSubmit},
                                    'Change password')
                            )
                        )
                    )
                )
        );
    },
    
    propTypes: {
        handleHideModal: React.PropTypes.func.isRequired
      }
});
//
//var renderPasswordSetting = function () {
//    React.render(
//        React.createElement(PasswordCard),
//        document.getElementById("passwordCard")
//    );
//};
