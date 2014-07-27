'use strict';
module.exports = function(grunt) {

  grunt.initConfig({
    pkg: grunt.file.readJSON("package.json"),
      less: {
        development: {
          options: {
            paths: ["resources/public/css"]
          },
          files: {
            "resources/public/css/main.css": "less/main.less"
          }
        },
        production: {
          options: {
            paths: ["resources/public/css"],
            cleancss: true
          },
          files: {
            "resources/public/main.css": "less/main.less"
          }
        }
      },
      watch: {
        less : {
            files: ['less/**/*.less'],
            tasks: ['less:development']
        }
      }
  });

  // Load tasks

  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-watch');

  // Register tasks
  grunt.registerTask("default", ["watch"]);
  grunt.registerTask("build", ["less:production"]);


};