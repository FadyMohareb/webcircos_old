var converter = new Showdown.converter();

var ResetPswdForm = React.createClass({displayName: "resetPswdForm",

    componentDidMount: function componentDidMount(){
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideResetModal);
        
    },
    
    handleSubmit: function (e) {
        e.preventDefault();
        
        var email = this.refs.email.getDOMNode().value.trim();
            $.ajax({
                url: "/resetPassword",
                dataType: 'json',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: email,
                success: function (data) {
    //                console.log(data)
                    if(data.errors == null) {
                        location = '/home';
                    }else
                    {
                        alert("Password reset failed");
                    }
                },
                error: function (xhr, status, err) {
                    console.error(status, err.toString());
                }
            });
            
        return
    },
    
    render: function () {
        return (React.createElement('div',{ className: 'modal fade', id: "resetPswdModal" },
                    React.createElement('div',{ className: 'modal-dialog' },
                        React.createElement('div',{ className: 'modal-content' },
                            React.createElement('div',{ className: 'modal-header' },
                                React.createElement('button',{ type: 'button', className: 'close',
                                    'data-dismiss': 'modal', 'aria-label': 'Close' },
                                    React.createElement('span',{ 'aria-hidden': 'true' },'\xD7')),
                            React.createElement('h3',{ className: 'modal-title' },'Reset password')),
                            React.createElement('div',{ className: 'modal-body' },
                                React.createElement('h6', null, "If you forgotten your password, enter your email and use 'Reset password button'. New password will be generated for you and sent by an e-mail. You can change your password after you sign in."),
                                React.createElement("label", {type: "text", htmlFor: "email"},
                                React.createElement('span',{ className: "glyphicon glyphicon-envelope" }), " E-mail:"),
                                React.createElement("input", {className:'form-control input-sm', type: "text", placeholder: "Email", ref: "email", id: "email"})),
                                React.createElement('div',{ className: 'modal-footer' },
                                React.createElement('button',{ type: 'button', className: 'btn btn-primary', onClick: this.handleSubmit},
                                    'Reset password')
                            )
                        )
                    )
                )
        );
    },
    
    propTypes: {
        handleHideResetModal: React.PropTypes.func.isRequired
      }
    
});