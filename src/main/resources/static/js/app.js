var app3 = new Vue({
    el: '#app',
    data: {
        seen: true,
        version1File: null,
        version2File: null,
        version3File: null,
        acyclicPathsAdded: false,
        acyclicPathsRemoved: false,
    },
    methods: {
        buildMVICFG: function() {
            console.log("version 1 file: " + this.version1File);
            console.log("version 2 file: " + this.version2File);
            console.log("version 3 file: " + this.version3File);

            console.log("AcyclicPathsAdded: " + this.acyclicPathsAdded);
            console.log("AcyclicPathsRemoved: " + this.acyclicPathsRemoved);

            console.log("building MVICFG...")
        },
        processFile(event, version) {

            if (version == 'version1') {
                this.version1File = event.target.files[0];
            } else if (version == 'version2') {
                this.version2File = event.target.files[0];
            } else if (version == 'version3') {
                this.version3File = event.target.files[0];
            }
        }
    }
})