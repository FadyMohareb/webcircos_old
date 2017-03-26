var converter = new Showdown.converter();

var NewProjModal = React.createClass({className: "newProjModal",
    componentDidMount: function componentDidMount() {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideNewProjModal);
    },
    handleSubmit: function (e) {
        e.preventDefault(); // needw a method to create new directory for project files
    },
    render: function () {
        return (React.createElement('div', {className: 'modal fade'},
                React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close',
                                            'data-dismiss': 'modal', 'aria-label': 'Close'},
                                            React.createElement('span',{ 'aria-hidden': 'true' },'\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'New project')),
                                React.createElement('div', {className: 'modal-body'}, "Project name: ",
                                        React.createElement('input', {type: 'text', ref: 'projectName'})),
                                React.createElement('div',{ className: 'modal-footer' },
                                React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},
                                'Create project')))
                ))
                );
    },
    
    propTypes: {
        handleHideNewProjModal: React.PropTypes.func.isRequired
      }
})