$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

var Diaries = Backbone.Collection.extend({
    url: '/Diary/Diary'
});

var Diary = Backbone.Model.extend({
    urlRoot: '/Diary/add',
});

var DiaryList = Backbone.View.extend({
    el: '#primary-content',

    render: function(options) {
        var that = this;
        var diaries = new Diaries();
        var template = _.template($('#indexTemplate').html());
        that.$el.html(template({
            diaries: this.collection.models
        }));
        diaries.fetch({
            success: function(diaries) {
            	var obj = {};
            	obj.diaries = diaries;
                var template = _.template($('#indexTemplate').html());
                that.$el.html(template({
                    diaries: obj.diaries.models
                    
                }));
                Backbone.history.navigate("", {
                    trigger: true
                }, 100);
            }
        })
    },
    events: {
    	"submit .search-entry" : "searchEntry"
    },
    
});

var Create = Backbone.View.extend({
    el: '#primary-content',
    render: function() {
        var template = _.template($('#formTemplate').html());
        this.$el.html(template);
        setTimeout(function() {
            tinymce.remove();
            tinymce.init({
            	selector: 'textarea',
          	  height: 150,
          	  theme: 'modern',
          	  plugins: [
          	        "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
          	        "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
          	        "contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern"
          	  ],
          	templates: [{
          	    title: 'Diary Templat4e',
          	    content: 'Test 1'
          	  }, {
          	    title: 'Travelogue',
          	    content: 'Test 2'
          	  }],
          	toolbar1: "newdocument fullpage | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect fontselect fontsizeselect",
            toolbar2: "cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image media code | insertdatetime preview | forecolor backcolor",
            toolbar3: "hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",
          	image_advtab: true,
          	  content_css: [
          	    '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
          	    '//www.tinymce.com/css/codepen.min.css'
          	  ]
            });
        }, 50);

    },
    events: {
        "submit .add-entry": "addDiary",
    },
    addDiary: function(ev) {
        var diaryEntry = $(ev.currentTarget).serializeObject();
        var entry = new Diary();
        entry.save(diaryEntry);
        setTimeout(function() {

			var simplebar = new Nanobar();
			simplebar.go(100);
        Backbone.history.navigate("", {
            trigger: true
        })},3200);
        return false;
    }
});


var UpdateEntry = Backbone.View.extend({
    el: '#primary-content',
    render: function(options) {
    	var that = this;
         var diary = new Diary({
            id: options.id
        });
        diary.fetch({
            success: function(diary) {

                var template = _.template($('#editTemplate').html());
                that.$el.html(template({
                    diary: diary
                }));
            }
        });
        setTimeout(function() {
            tinymce.remove();
            tinymce.init({
            	selector: 'textarea',
            	  height: 150,
            	  theme: 'modern',
            	  plugins: [
            	        "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
            	        "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            	        "contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern"
            	  ],
            	templates: [{
            	    title: 'Test template 1',
            	    content: 'Test 1'
            	  }, {
            	    title: 'Test template 2',
            	    content: 'Test 2'
            	  }],
            	toolbar1: "newdocument fullpage | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect fontselect fontsizeselect",
            	toolbar2: "cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image media code | insertdatetime preview | forecolor backcolor",
            	toolbar3: "hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",
            	image_advtab: true,
            	  content_css: [
            	    '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
            	    '//www.tinymce.com/css/codepen.min.css'
            	  ]
            });
        }, 1100);
        
    },
});

var ViewEntry = Backbone.View.extend({
    el: '#primary-content',
    render: function(options) {
        var that = this;
        var diary = new Diary({
            id: options.id
        });
        diary.fetch({
            success: function(diary) {
            	var obj = {};
            	obj.diary = diary;
                var template = _.template($('#viewTemplate').html());
                that.$el.html(template({
                    diary: obj.diary
                }));
            }
        });
    },
});


var RemoveEntry = Backbone.View.extend({
    el: '#primary-content',
    render: function(options) {
        var that = this;
        var diary = new Diary({
            id: options.id
        });
        diary.destroy();
        setTimeout(function() {
			var simplebar = new Nanobar();
			simplebar.go(100);
        Backbone.history.navigate("", {
            trigger: true
        })}, 3200);


    },
});



var Router = Backbone.Router.extend({
    routes: {
        '': 'home',
        'new': 'Create',
        'edit/:id': 'UpdateEntry',
        'Details/:id': 'ViewEntry',
        'remove/:id': 'RemoveEntry',


    },
    initialize: function() {
        this.diaries = new Diaries();
        this.listenTo(this.diaries, 'change', this.home);
        this.diaries.fetch();

        this.diaryList = new DiaryList({
            collection: this.diaries
        });

    },
    home: function() {
        this.diaryList.render();
    }
});




var create = new Create();
var update = new UpdateEntry();
var view = new ViewEntry();
var router = new Router();
var remove = new RemoveEntry();

router.on('route:Create', function() {
    create.render();    
});

router.on('route:UpdateEntry', function(id) {
    update.render({
        id: id 
        });
});

router.on('route:ViewEntry', function(id) {
    view.render({
        id: id
    });
    
});

router.on('route:RemoveEntry', function(id) {
    remove.render({
        id: id
    });
    
});





Backbone.history.start();


