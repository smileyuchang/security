$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'power/powercoalgas/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
            { label: '承租方', name: 'username', index: 'username', width: 80 },
            { label: '手机号', name: 'mobile', index: 'mobile', width: 80 },
			{ label: '煤气用量', name: 'number', index: 'number', width: 80 }, 			
			{ label: '煤气号', name: 'coal_gas_number', index: 'coal_gas_number', width: 80 },
			{ label: '收费时间', name: 'pay_time', index: 'pay_time', width: 80 },
			{ label: '创建时间', name: 'create_time', index: 'create_time', width: 80 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		powerCoalGas: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},

        importUser: function(){//导入用户
            //var userId = getSelectedRow();
            //vm.q.userId = userId;
            //if (userId != null){
            $('#importModal').modal();
            //}
        },

        uploadFile: function (event) {
            $("#resume_frm").ajaxSubmit({
                type:"post",
                url:baseURL + "power/powercoalgas/importCoalgas",
                dataType:"json",
                beforeSend:function(xhr){
                    $('#importModal').modal('hide');
                    layer.msg('导入中...', {
                        icon: 16,
                        shade: 0.5,
                        time:false // 取消自动关闭
                    });
                },
                success:function(data){
                    // vm.q.userId = null;
                    layer.alert(data.msg, {
                        icon: 1,
                        skin: 'layer-ext-moon'
                    })
                    // layer.closeAll('loading');
                    vm.reload();
                } // end success
            });
        },

        importModelHide:function(){
            $('#importModal').modal('hide');
        },



		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.powerCoalGas = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.powerCoalGas.id == null ? "power/powercoalgas/save" : "power/powercoalgas/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.powerCoalGas),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "power/powercoalgas/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "power/powercoalgas/info/"+id, function(r){
                vm.powerCoalGas = r.powerCoalGas;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});