var gulp = require('gulp');
var bower = require('gulp-bower');
var flatten = require('gulp-flatten');
var nodemon = require('gulp-nodemon');
var prefix = require('gulp-autoprefixer');

var public = __dirname + '/app/public'
console.log(public);

gulp.task('bower', function() {
  return bower()
    .pipe(gulp.dest('vendor'));
})

gulp.task('flatten-js', ['bower_components'], function() {
  return gulp.src('vendor/**/*.min.js')
    .pipe(flatten())
    .pipe(gulp.dest(public + '/js' + '/vendor'));
});

gulp.task('flatten-css', ['bower'], function() {
  return gulp.src('vendor/**/*.css')
    .pipe(flatten())
    .pipe(gulp.dest(public + '/css' + '/vendor'));
});

gulp.task('flatten-images', ['bower'], function() {
  return gulp.src('vendor/**/images/*')
    .pipe(flatten())
    .pipe(gulp.dest(public + '/images' + '/vendor'));
});


gulp.task('default', ['bower']);
