$(function() {

	$.extend($.jgrid.defaults, {
				datatype: 'json',
				jsonReader : {
					repeatitems:false,
					total: function(result) {
						//Total number of pages
						return Math.ceil(result.total / result.max);
					},
					records: function(result) {
						//Total number of records
						return result.total;
					}
				},
				prmNames: {
					page: "page.page",
					rows: "page.size",
					sort: "page.sort",
					order: "page.sort.dir"
				},
				sortname: 'firstName',
				sortorder: 'asc',
				height: 'auto',
				viewrecords: true,
				rowNum:10,
				rowList: [10, 20, 50, 100],
				altRows: true,
				loadError: function(xhr, status, error) {
					alert(error);
				}
			});

	$.extend($.jgrid.edit, {
				closeAfterEdit: true,
				closeAfterAdd: true,
				ajaxEditOptions: { contentType: "application/json" },
				mtype: 'PUT',
				serializeEditData: function(data) {
					delete data.oper;
					return JSON.stringify(data);
				}
			});
	$.extend($.jgrid.del, {
				mtype: 'DELETE',
				serializeDelData: function() {
					return "";
				}
			});

	var editOptions = {
		onclickSubmit: function(params, postdata) {
			params.url = BASE_URL + postdata.id;
		}
	};
	var addOptions = {
		mtype: "POST",
		recreateForm: true,
		beforeShowForm: function(form) {
			var nameColumnField = $('#tr_telephone', form).show();
			//TODO:Client side captcha implemantation
			$('<tr class="FormData" id="tr_Captcha"><td class="CaptionTD ui-widget-content"><b>Captcha:</b>' + '' + '</td></tr>').insertAfter (nameColumnField);
		}
	};
	var delOptions = {
		onclickSubmit: function(params, postdata) {
			params.url = BASE_URL  + "delete/" + postdata;
		}
	};

	var BASE_URL = 'http://localhost:8080/Jqgrid_Spring_Mongodb/users/';
	var options = {
		url: BASE_URL + "records",
		editurl: BASE_URL,
		colModel:[
			{
				name:'id',
				label: 'ID',
				index: 'id',
				width: 40,
				editable: false,
				hidden:true,
				editoptions: {disabled: true, size:5},
				sortable:false
			},
			{
				name:'firstName',
				label: 'Ad',
				index: 'firstName',
				width: 200,
				editable: true,
				editrules: {required: true},
				sortable:false
			},
			{
				name:'lastName',
				label: 'Soyad',
				index: 'lastName',
				width: 200,
				editable: true,
				editrules: {required: true},
				sortable:false
			},
			{
				name:'telephone',
				label: 'Telefon',
				index: 'telephone',
				width: 200,
				sortable:false,
				editable: true,
				editoptions: {dataInit: function (elem) { $(elem).mask("(999) 999-99-99"); }},
			    editrules: {custom: true, custom_func: function (value) {
			        if (value.length>0) {
			            return [true, ""];
			        } else {
			            return [false, "hatalı telefon girişi"];
			        }
			    }}
			}
		],
		caption: "Kişiler",
		pager : '#pager',
		height: 'auto',
		ondblClickRow: function(id) {
			jQuery(this).jqGrid('editGridRow', id, editOptions);
		}
	};

	$("#grid")
			.jqGrid(options)
			.navGrid('#pager',
			{ edit: true, add: true, del: true, search: false, refresh:false }, //options
			editOptions,
			addOptions,
			delOptions,
			{} // search options
	);

});
