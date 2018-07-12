var common = {
	/**
	 * @author zhoujiawei
	 * 将null或"null"字符串置为""
	 * @param obj
	 * @returns
	 */
	null2empty : function(obj) {
	    if (!obj || "null" == obj)
	        return "";
	    for (var p in obj) {
//	        if (obj[p] instanceof Object) {
//	            obj[p] = null2empty(obj[p]);
//	        } else if (!obj[p] || "null" == obj[p]) {
//	            obj[p] = "";
//	        }
	        if (!obj[p] || "null" == obj[p]) {
	            obj[p] = "";
	        }
	    }
	    return obj;
	},
	/**
	 * 将null或"null"字符串置为"0"
	 * @author zhoujiawei
	 * @param obj
	 * @returns
	 */
	null2zero : function(obj) {
	    return common.isEmpty(obj) ? "0" : obj;
	},
	/**
	 * 判断是否为空
	 * @author zhoujiawei
	 * @param obj
	 * @returns {Boolean}
	 */
	isEmpty : function(obj) {
		if(obj == undefined || obj.length == 0 || obj == null) {
	        return true;
	    }
	    return false;
	},
	/**
	 * 判断是否不为空
	 * @author zhoujiawei
	 * @param obj
	 * @returns
	 */
	isNotEmpty : function(obj) {
	    return !common.isEmpty(obj);
	},
	/**
	 * 弹出层
	 * @author zhoujiawei
	 * @param eleId
	 */
	openLayer : function(eleId){
		layerIndex = layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: '500px auto',
			  shadeClose: false,
			  content: $('#'+eleId)
		});
		$('.layui-layer-content').css('overflow','hidden');
		return layerIndex;
	},
	/**
	 * 关闭弹出层
	 * @author zhoujiawei
	 */
	closeLayer : function(){
		layer.close(layerIndex);
	},
	/**
	 * 生成uuid，在grid中添加数据(addRowData方法)时没有id时使用
	 * @author zhoujiawei
	 * @returns
	 */
	uuid : function() {
	    var s = [];
	    var hexDigits = "0123456789abcdef";
	    // 设置uuid长度32位
	    for (var i = 0; i < 32; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
//	    s[8] = s[13] = s[18] = s[23] = "-"; // "-"分隔，和guid相似
	 
	    var uuid = s.join("");
	    return uuid;
	},
	/**
	 * 生成日期(年月日)
	 * @author zhoujiawei
	 * @param regex 分隔符
	 * @param count 减去的时间
	 */
	systemDate : function(regex, count) {
		var date = new Date();
		// 年
		var year = date.getFullYear() - count;
		// 月
		var month = date.getMonth()+1;
		// 日
		var day = date.getDate();
		// 如果月份是个位数，在月份前拼接0
		if (month.toString().length == 1) {
			month = "0" + month;
		}
		// 如果日期是个位数，在日期前拼接0
		if (day.toString().length == 1) {
			day = "0" + day;
		}
		return year + regex + month + regex + day;
	},
	
	/**
	 * 库区和库位的联动
	 * @author zhoujiawei
	 * @param areaId 库区在html中的id属性值
	 * @param positionId 库位在html中的id属性值
	 * @param status 状态区分(0为全部库位；1为未删除库位；2为已删除库位)
	 * @param type 状态区分(1为全部库位；2为除特殊库位)
	 */
	linkage : function(areaId, positionId, status, type, focusId) {
		$('#' + areaId).combobox({
			onChange : function(newValue, oldValue) {
				// 重新加载库位
				$('#' + positionId).combobox('reload', 
						'commondata/searchAllPosition?areaId=' + newValue + "&status=" + status + '&type=' + type);
				// 若库区发生改变，库位应变成“请选择”
				$('#' + positionId).combobox('setValue', -1);
			},
			onLoadSuccess : function() {
				// 加载成功后，判断focusId不为空的情况下，有对应focusId的选择器，聚焦
				if (common.isNotEmpty(focusId)) {
					if (common.isNotEmpty(document.getElementById(focusId))) {
						document.getElementById(focusId).focus();
					}
				}
			}
		})
	},
	
	linkages : function(areaId, positionId, status, type, focusId) {
		$('#' + areaId).combobox({
			onChange : function(newValue, oldValue) {
				// 初始化签收入库的flag
				isFirst = true;
				isInstockSuccess = false;
				// 重新加载库位
				$('#' + positionId).combobox('reload', 
						'commondata/searchPosition?areaId=' + newValue + "&status=" + status + '&type=' + type);
				// 若库区发生改变且库位下拉框为空时，库位应变成“请选择”
//				if (common.isEmpty($('#' + positionId).combobox('getValue'))) {
					$('#' + positionId).combobox('setValue', -1);
//				}
			},
		onLoadSuccess : function() {
			// 加载成功后，判断focusId不为空的情况下，有对应focusId的选择器，聚焦
			if (common.isNotEmpty(focusId)) {
				if (common.isNotEmpty(document.getElementById(focusId))) {
					document.getElementById(focusId).focus();
				}
			}
		}
		})
	},
	
	/**
	 * 插入行数据，并修改grid中手动插入行的背景色，字体色
	 * @author zhoujiawei
	 * @param grid
	 * @param data
	 * @param insertRow
	 * @param deleteRow
	 * @param isSign
	 */
	insertAndChangeRow : function(grid, data, insertRow, deleteRow, isSign) {
		var rows = grid.datagrid('getRows');
		if (deleteRow + 1 == rows.length) {
			// 移除传入行的记录
			grid.datagrid('deleteRow', deleteRow);
		} else if (rows.length > 3) {
			$.messager.alert("提示信息", "程序出现异常,请联系管理员!");
			return false;
		}
		
		// 在datagrid中插入后台返回的一条数据（datagrid下标从0开始）
		grid.datagrid('insertRow',{'index' : insertRow, 'row' : data});
		
		var id = grid.prev().children().children().children('tbody').children()[insertRow].id;
		
		// 如果isSign为空，表示是入库操作
		if (common.isNotEmpty(isSign)) {
			changeRowStyle(id, 1);
		} else {
			changeRowStyle(id, 3);
		}
		
		// datagird的样式
		$('.datagrid-cell').css('font-size', '15px');
		$('.datagrid-row').css('height','50px');
	},
	
	/**
	 * 使用定时器，根据定时器时间恢复表单中的行样式
	 * @author zhoujiawei
	 * @param gridId
	 * @param timeout
	 * @param index
	 */
	cleanStyle : function(grid, timeout, index) {
//		if (grid.prev().children().children().children('tbody').children().length == 3) {
//			var oldId = grid.prev().children().children().children('tbody').children()[1].id;
//			setTimeout(function() {
//				changeRowStyle(oldId, 2);
//			}, 1500);
//		}
		var id = grid.prev().children().children().children('tbody').children()[index].id;
		setTimeout(function() {
			changeRowStyle(id, 2);
		}, timeout);
	},
	
	/**
	 * 需要打印部分的html(并且将没有值的字段设置成空字符串)
	 * @author zhoujiawei
	 * @param rowData
	 * @param index
	 * @param tableId
	 * @param isPtrqt 是否是回运标签(是:true)
	 * @returns {String}
	 */
	drawHtml : function(rowData, index, tableId, isPtrqt) {
		if (isPtrqt) {
			 var html = '<div style="width:99%;">' +
							'<table id="' + tableId + index + '" class="ptrqt" style="font-size:16pt;">' +
								'<tr style="height:20px;"></tr>' +
								'<tr>' +
									'<td>UIN:&nbsp;&nbsp;' + common.null2empty(rowData.uin) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>BAC代码:&nbsp;&nbsp;' + common.null2empty(rowData.warrantyCode) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>GWM索赔单号:&nbsp;&nbsp;' + common.null2empty(rowData.gwmClaimno) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>WOL索赔单号:&nbsp;&nbsp;' + common.null2empty(rowData.dealerClaimno) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>车牌号:&nbsp;&nbsp;' + common.null2empty(rowData.licenseNo) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>支付日期:&nbsp;&nbsp;' + common.null2empty(rowData.processDatetime) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>VIN:&nbsp;&nbsp;' + common.null2empty(rowData.vin) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>里程数:&nbsp;&nbsp;' + common.null2empty(rowData.odometerReading) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>故障代码:&nbsp;&nbsp;' + common.null2empty(rowData.troubleCode) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>故障原因:&nbsp;&nbsp;' + (common.null2empty(rowData.troubleReason).length > 15 ? common.null2empty(rowData.troubleReason).substr(0, 15) : common.null2empty(rowData.troubleReason)) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>索赔配件号:&nbsp;&nbsp;' + common.null2empty(rowData.partCode) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>零件名称:&nbsp;&nbsp;' + common.null2empty(rowData.partName) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>换下配件号:&nbsp;&nbsp;' + common.null2empty(rowData.replacePartCode) + '</td>' +
								'</tr>' +
								'<tr>' +
									'<td>换下零件名称:&nbsp;&nbsp;' + common.null2empty(rowData.replacePartName) + '</td>' +
								'</tr>' +
								'<tr style="height:5px;"></tr>' +
								'<tr>' +
									'<td>' +
										'<div id="qrcode' + index + '" style="display:none;"></div>' +
										'&nbsp;&nbsp;<img id="qrcodeImg' + index +'"></div>' +
										'</td>' +
								'</tr>' +
								'<tr class="lastPtrqtTr" style="height:72px;"></tr>' +
//								'<tr><td>-----------------------</td></tr>' +
							'</table>' +
						'</div>';
			return html;
		} else {
			 var html = '<div class="stock" style="font-size:20pt;">' +
			 				'<div style="height:20px;"></div>' +
			 				'<div style="height:70px;">' + 
			 					'<span>库区名称:&nbsp;&nbsp;</span>' +
			 					'<span style="display:inline-block;width:240px;">' + (common.null2empty(rowData.areaName).length > 15 ? common.null2empty(rowData.areaName).substr(0, 15) : common.null2empty(rowData.areaName)) + '</span>' +
			 				'</div>' +
			 				'<div style="height:70px;">' + 
			 					'<span>库位名称:&nbsp;&nbsp;</span>' +
								'<span style="display:inline-block;width:240px;">' + (common.null2empty(rowData.positionName).length > 15 ? common.null2empty(rowData.positionName).substr(0, 15) : common.null2empty(rowData.positionName)) + '</span>' +
			 				'</div>' +
			 				'<div style="height:70px;">' + 
			 					'<span>库位容量:&nbsp;&nbsp;</span>' +
								'<span style="display:inline-block;width:240px;">' + common.null2empty(rowData.positionCapacity) + '</span>' +
			 				'</div>' +
			 				'<div style="height:70px;">' + 
			 					'<span>库位属性:&nbsp;&nbsp;</span>' +
								'<span style="display:inline-block;width:240px;">' + common.null2empty(rowData.type) + '</span>' +
			 				'</div>' +
			 				'<div style="height:70px;">' + 
			 					'<span>配件代码:&nbsp;&nbsp;</span>' +
								'<span style="display:inline-block;width:240px;">' + common.null2empty(rowData.partCode) + '</span>' +
			 				'</div>' +
			 				'<div style="height:70px;">' + 
			 					'<span>配件名称:&nbsp;&nbsp;</span>' +
								'<span style="display:inline-block;width:240px;">' + (common.null2empty(rowData.partName).length > 15 ? common.null2empty(rowData.partName).substr(0, 15) : common.null2empty(rowData.partName)) + '</span>' +
			 				'</div>' +
			 				'<div style="height:235px;">' +
								'<div id="qrcode' + index + '" style="display:none;"></div>' +
								'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + 
								'<img id="qrcodeImg' + index +'"></div>' +
			 				'</div>' +
//							'<table id="' + tableId + index + '" class="stock" style="font-size:20pt;">' +
//								'<tr class="lastPositionTr" style="height:40px;"></tr>' +
//								'<tr style="height:65px;">' +
//									'<td>库区名称:&nbsp;</td>' +
//									'<td style="width:240px;">' + (common.null2empty(rowData.areaName).length > 15 ? common.null2empty(rowData.areaName).substr(0, 15) : common.null2empty(rowData.areaName)) + '</td>' +
//								'</tr>' +
//								'<tr style="height:65px;">' +
//									'<td>库位名称:&nbsp;</td>' +
//									'<td style="width:240px;">' + (common.null2empty(rowData.positionName).length > 15 ? common.null2empty(rowData.positionName).substr(0, 15) : common.null2empty(rowData.positionName)) + '</td>' +
//								'</tr>' +
//								'<tr style="height:65px;">' +
//									'<td>库位容量:&nbsp;</td>' +
//									'<td style="width:240px;">' + common.null2empty(rowData.positionCapacity) + '</td>' +
//								'</tr>' +
//								'<tr style="height:65px;">' +
//									'<td>库位属性:&nbsp;</td>' +
//									'<td style="width:240px;">' + common.null2empty(rowData.type) + '</td>' +
//								'</tr>' +
//								'<tr style="height:65px;">' +
//									'<td>配件代码:&nbsp;</td>' +
//									'<td style="width:240px;">' + common.null2empty(rowData.partCode) + '</td>' +
//								'</tr>' +
//								'<tr style="height:65px;">' +
//									'<td>配件名称:&nbsp;</td>' +
//									'<td style="width:240px;">' + (common.null2empty(rowData.partName).length > 15 ? common.null2empty(rowData.partName).substr(0, 15) : common.null2empty(rowData.partName)) + '</td>' +
//								'</tr>' +
//								'<tr style="height:16px;"></tr>' +
//								'<tr>' +
//									'<td colspan="2">' +
//										'<div id="qrcode' + index + '" style="display:none;"></div>' +
//											'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + 
//											'<img id="qrcodeImg' + index +'"></div>' +
//									'</td>' +
//								'</tr>' +
//								'<tr class="lastPositionTr" style="height:5px;"></tr>' +
////								'<tr><td style="display:none;">------------------------</td></tr>' +
//							'</table>' +
						'</div>';
			return html;
		}
	},
	
	/**
	 * 打印多条
	 * @author zhoujiawei
	 * @param data
	 * @param $dialog
	 * @param tableId
	 * @param isPtrqt 是否是回运标签(是：true)
	 */
	printList : function(data, $dialog, $tableDiv, tableId, isPtrqt) {
		for (var i = 0; i < data.length; i++) {
			// 获得重画的html标签
			var html = common.drawHtml(data[i], i, tableId, isPtrqt);
			// 在标签中重画table的html标签
			$tableDiv.append(html);
			
			// 回运清单标签的二维码大小和库区库位标签二维码大小不同，url不同
			var url;
			var qrcodeId = '#qrcode' + i;
			if (isPtrqt) {
				url = data[i].uin;
				// 生成二维码
				$(qrcodeId).qrcode({
					render : 'canvas', //二维码渲染方式{canvas,table}.canvas比较好，但要求浏览器能支持html5
					width : 110,
					height : 110,
					text : url
				});
			} else {
				url = 'views/wpcScanChange/checkAreaAndPosition?areaId=' + data[i].areaId + '&positionId=' + data[i].positionId;
				// 生成二维码
				$(qrcodeId).qrcode({
					render : 'canvas', //二维码渲染方式{canvas,table}.canvas比较好，但要求浏览器能支持html5
					width : 180,
					height : 180,
					text : url
				});
			}
			
			// 将生成的二维码转换成图片(不然打印不出二维码)
			var canvas = $(qrcodeId + ' canvas');
			var img = canvas[0].toDataURL("image/png");
			$('#qrcodeImg' + i).attr('src', img);
//			$(qrcodeId).html("<img src='" + img + "'>")
		}
		
		// 显示div内容
		$dialog.show();
		// 初始化dialog
		$dialog.dialog({
			title : '打印预览',
			width : 400,
			height : 530,
			cache : false,
			modal : true,
			maximizable : true, // dialog最大化按钮
//			minimizable:true, // dialog最小化按钮
			collapsible:true, // dialog伸缩按钮
			resizable : false, // 窗口不能缩放
//			shadow : true, // 窗体阴影
			buttons : [{
				id : tableId + 'closeBtn',
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					$dialog.dialog('close');
				}
			}]	
		});
		// 打印预览窗口居中显示
		$dialog.dialog('center');
		// 修改dialog中按钮的位置
		$('#' + tableId + 'closeBtn').attr('style', 'width:70px;margin-right:39%;');
		
		// 打印table的父标签(若打印dialog对应的div，windows系统下会把滚动条一起打印出来)
		$tableDiv.print();
	},
	 
	/**
	 * 页面预览style修改
	 * @author zhoujiawei
	 * @param length
	 * @param $dialog
	 * @param tableId
	 * @param isPtrqt 是否是回运标签(是：true)
	 */
	changeStyle : function(length, tableId, isPtrqt) {
		// 移除定位
		if (isPtrqt) {
			for (var i = 0; i < length; i++) {
				$('#' + tableId + i).attr('style', 'font-size:10pt;padding-left:15%;height:432px;')
			}
		} else {
			for (var i = 0; i < length; i++) {
				$('#' + tableId + i).attr('style', 'font-size:13pt;padding-left:15%;height:432px;')
			}
		}
		
//		var style = $dialog.attr('style');
//		var index = style.indexOf('overflow: hidden;');
//		if (index == 0) {
//			var startIndex = style.indexOf('hidden;') + 'hidden; '.length + 1;
//			style = style.substr(startIndex, style.length);
//		}
//		$dialog.attr('style', style + 'overflow-y:scroll;');
	},
	
}

/**
 * 修改grid中手动插入行的背景色，字体色
 * @author zhoujiawei
 * @param grid
 * @param status
 */
function changeRowStyle(id, status) {
	// 获得tr的style属性
	var style = $('#' + id).attr('style');
	var index = style.indexOf('background-color');
	if (index >= 0) {
		// 下标中如果已经存在了背景色，说明已经被手动修改过style，截断
		style = style.substr(0, index);
	}
	
	// 如果状态是1，样式变成红色；状态是2，变回默认样式；如果状态0，样式
	if (status == 1) {
		style = style + 'background-color:pink;color:blue;font-weight:bold;';
	} else if (status == 2) {
		style = style + 'background-color:write;color:black;font-weight:bold;';
	} else if (status == 3) {
		style = style + 'color:red;font-weight:bold;';
	}
	$('#' + id).attr('style', style);
}
