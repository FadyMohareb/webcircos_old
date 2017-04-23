/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var converter = new Showdown.converter();

var RemoveModal = React.createClass({displayName: "RemoveModal",
    
    componentDidMount: function componentDidMount(){
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideRemoveModal);
        
    },
    
    handleSubmit: function (e) {
        alert('Needs ajax and back for removing files');
    },
    render: function () {
        return (React.createElement('div',{ className: 'modal fade', id: "removeModal" },
                    React.createElement('div',{ className: 'modal-dialog' },
                        React.createElement('div',{ className: 'modal-content' },
                            React.createElement('div',{ className: 'modal-header' },
                                React.createElement('button',{ type: 'button', className: 'close', 'aria-label': 'Close',
                                'data-dismiss': 'modal'},
                                    React.createElement('span',{ 'aria-hidden': 'true' },'\xD7')),
                            React.createElement('h3',{ className: 'modal-title' },'Remove files from project')),
                            React.createElement('div',{ className: 'modal-body' },
                            React.createElement('h3', null, 'Modal content')
                            ),
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