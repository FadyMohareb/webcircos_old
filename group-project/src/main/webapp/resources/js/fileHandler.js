var dropZone = document.getElementById('drop_zone');
dropZone.addEventListener('dragover', handleDragOver, false);
dropZone.addEventListener('drop', handleFileSelect, false);
document.getElementById('files').addEventListener('change', handleFileSelect);

function handleFileSelect(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    var files = evt.target.files || evt.dataTransfer.files;
    var output = [];
    try
    {
        var FilePath = evt.target.value;
    }
    catch(err){};
    var FileName = files[0].name;
    var FileSize = files[0].size;
    var FileType = "";

    if (FileName.includes("fasta"))
        FileType = "fasta";
    else if (FileName.includes("fa"))
        FileType = "fasta";
    else if (FileName.includes("frn"))
        FileType = "fasta";
    else if (FileName.includes("ffn"))
        FileType = "fasta";
    else if (FileName.includes("gff"))
        FileType = "gff";
    else if (FileName.includes("vcf"))
        FileType = "vcf";
    else
    {
        var start = 0;
        var stop = 5;
        var reader = new FileReader();

        var blob = files[0].slice(start, stop + 1);
        reader.readAsBinaryString(blob);
        reader.onload = function ()
        {
            var text = reader.result;
            console.log(reader.result.substring(0, 200));
            if (text.includes(">"))
                FileType = "fasta";
            else if (text.includes("##gff"))
                FileType = "gff";
            else if (text.includes("##fileformat=VCF"))
                FileType = "vcf";
            else
                FileType = "undeffined";
        };
    }
    output.push('<li><strong>', escape(FileName), '</strong> (', FileType || 'n/a', ') - ', FileSize, ' bytes, path: ', FilePath, '</li>');
    document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

function handleDragOver(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    console.log();
    evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.

}