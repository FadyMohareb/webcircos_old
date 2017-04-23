/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var converter = new Showdown.converter();

var ManualModal = React.createClass({displayName: "ManualModal",
    
    componentDidMount: function componentDidMount(){
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideManualModal);
        
    },
    
    render: function () {
        return (React.createElement('div',{ className: 'modal fade', id: "manualModal" },
                    React.createElement('div',{ className: 'modal-dialog' },
                        React.createElement('div',{ className: 'modal-content' },
                            React.createElement('div',{ className: 'modal-header' },
                                React.createElement('button',{ type: 'button', className: 'close', 'aria-label': 'Close',
                                'data-dismiss': 'modal'},
                                    React.createElement('span',{ 'aria-hidden': 'true' },'\xD7')),
                            React.createElement('h3',{ className: 'modal-title' },'WebCircos manual')),
                            React.createElement('div',{ className: 'modal-body' },
                            React.createElement('h3', null, 'User manual')
                            )
                            )
                        )
                    )
                
        );
    },
    
    propTypes: {
        handleHideManualModal: React.PropTypes.func.isRequired
      }
});