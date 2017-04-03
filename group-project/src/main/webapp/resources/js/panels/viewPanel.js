var converter = new Showdown.converter();

var ViewPanel = React.createClass({className: "viewPanel",
    // Needs a method to chceck if required files are uploaded and enable views
    handleSubmit: function (e) {
         e.preventDefault();
         alert('This button will refresh the circos');
         //send the list of file that need to be parsed or the list of needed objects.

    },
    
    render: function () { 
        return (
                React.createElement('div', {className: "panel panel-primary"},
                        React.createElement('div', {className: "panel-heading"}, "View settings"),
                        React.createElement('div', {className: "panel-body"},
                        React.createElement('input', { type: 'checkbox' }, " Reads depth"),
                        React.createElement('br'),
                        React.createElement('input', { type: 'checkbox' }, " SNP density"),
                        React.createElement('br'),
                                React.createElement('input', { type: 'checkbox' }, " Genes expression"),
                                React.createElement('br'),
                                React.createElement('input', { type: 'checkbox' }, " Differential expression"),
                                React.createElement('br'),
                                React.createElement('input', { type: 'checkbox' }, " Parent-pool"),
                                React.createElement('br'),
                                React.createElement('br'),
                                React.createElement('button', { className: 'btn btn-primary', onClick: this.handleSubmit }, "Update circos")
        )));
    }
})

var renderViewPanel = function () {
    React.render(React.createElement(ViewPanel), document.getElementById('rightContainer'));
};

