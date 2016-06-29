!function ($) {
    'use strict';
    
    
var MyTable = function (el, options) {
    this.options = options;
    this.$el = $(el);
    this.$el_ = this.$el.clone();
    this.init();
};
            
MyTable.prototype.init = function () {   
    this.initTable();
 //   this.loadData();
};

MyTable.prototype.initTable = function () {   
    var that = this,
    columns = [],
    data = [];
    this.$header = this.$el.find('>thead');
    if (!this.$header.length) {
        this.$header = $('<thead></thead>').appendTo(this.$el);
    }
    this.$header.find('tr').each(function () {
        var column = [];

        $(this).find('th').each(function () {
            column.push($.extend({}, {
                title: $(this).html(),
                'class': $(this).attr('class'),
                titleTooltip: $(this).attr('title'),
                rowspan: $(this).attr('rowspan') ? +$(this).attr('rowspan') : undefined,
                colspan: $(this).attr('colspan') ? +$(this).attr('colspan') : undefined
            }, $(this).data()));
        });
        columns.push(column);
    });
    if (!$.isArray(this.options.columns[0])) {
        this.options.columns = [this.options.columns];
    }
    this.options.columns = $.extend(true, [], columns, this.options.columns);
    this.columns = [];
};



$.fn.myTable = function (option) {
    var value,
        args = Array.prototype.slice.call(arguments, 1);
    this.each(function () {
    	alert($(this).text())
        var $this = $(this),
        data = $this.data('bootstrap.table'),
        options = $.extend({}, MyTable.DEFAULTS, $this.data(),
            typeof option === 'object' && option);
        if (!data) {
            $this.data('bootstrap.table', (data = new MyTable(this, options)));
        }
    });

    return typeof value === 'undefined' ? this : value;
};


$.fn.myTable.Constructor = MyTable;
$(function () {
	
    $('#statuTable').myTable();
});



MyTable.DEFAULTS = {
        classes: 'table table-hover',
        locale: undefined,
        height: undefined,
        undefinedText: '-',
        sortName: undefined,
        sortOrder: 'asc',
        striped: false,
        columns: [[]],
        data: [],
        dataField: 'rows',
        method: 'get',
        url: undefined,
        ajax: undefined,
        cache: true,
        contentType: 'application/json',
        dataType: 'json',
        ajaxOptions: {},
        queryParams: function (params) {
            return params;
        },
        queryParamsType: 'limit', // undefined
        responseHandler: function (res) {
            return res;
        },
        pagination: false,
        onlyInfoPagination: false,
        sidePagination: 'client', // client or server
        totalRows: 0, // server side need to set
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        paginationHAlign: 'right', //right, left
        paginationVAlign: 'bottom', //bottom, top, both
        paginationDetailHAlign: 'left', //right, left
        paginationPreText: '&lsaquo;',
        paginationNextText: '&rsaquo;',
        search: false,
        searchOnEnterKey: false,
        strictSearch: false,
        searchAlign: 'right',
        selectItemName: 'btSelectItem',
        showHeader: true,
        showFooter: false,
        showColumns: false,
        showPaginationSwitch: false,
        showRefresh: false,
        showToggle: false,
        buttonsAlign: 'right',
        smartDisplay: true,
        escape: false,
        minimumCountColumns: 1,
        idField: undefined,
        uniqueId: undefined,
        cardView: false,
        detailView: false,
        detailFormatter: function (index, row) {
            return '';
        },
        trimOnSearch: true,
        clickToSelect: false,
        singleSelect: false,
        toolbar: undefined,
        toolbarAlign: 'left',
        checkboxHeader: true,
        sortable: true,
        silentSort: true,
        maintainSelected: false,
        searchTimeOut: 500,
        searchText: '',
        iconSize: undefined,
        iconsPrefix: 'glyphicon', // glyphicon of fa (font awesome)
        icons: {
            paginationSwitchDown: 'glyphicon-collapse-down icon-chevron-down',
            paginationSwitchUp: 'glyphicon-collapse-up icon-chevron-up',
            refresh: 'glyphicon-refresh icon-refresh',
            toggle: 'glyphicon-list-alt icon-list-alt',
            columns: 'glyphicon-th icon-th',
            detailOpen: 'glyphicon-plus icon-plus',
            detailClose: 'glyphicon-minus icon-minus'
        },

        rowStyle: function (row, index) {
            return {};
        },

        rowAttributes: function (row, index) {
            return {};
        },

        onAll: function (name, args) {
            return false;
        },
        onClickCell: function (field, value, row, $element) {
            return false;
        },
        onDblClickCell: function (field, value, row, $element) {
            return false;
        },
        onClickRow: function (item, $element) {
            return false;
        },
        onDblClickRow: function (item, $element) {
            return false;
        },
        onSort: function (name, order) {
            return false;
        },
        onCheck: function (row) {
            return false;
        },
        onUncheck: function (row) {
            return false;
        },
        onCheckAll: function (rows) {
            return false;
        },
        onUncheckAll: function (rows) {
            return false;
        },
        onCheckSome: function (rows) {
            return false;
        },
        onUncheckSome: function (rows) {
            return false;
        },
        onLoadSuccess: function (data) {
            return false;
        },
        onLoadError: function (status) {
            return false;
        },
        onColumnSwitch: function (field, checked) {
            return false;
        },
        onPageChange: function (number, size) {
            return false;
        },
        onSearch: function (text) {
            return false;
        },
        onToggle: function (cardView) {
            return false;
        },
        onPreBody: function (data) {
            return false;
        },
        onPostBody: function () {
            return false;
        },
        onPostHeader: function () {
            return false;
        },
        onExpandRow: function (index, row, $detail) {
            return false;
        },
        onCollapseRow: function (index, row) {
            return false;
        },
        onRefreshOptions: function (options) {
            return false;
        },
        onResetView: function () {
            return false;
        }
    };    

}(jQuery);
