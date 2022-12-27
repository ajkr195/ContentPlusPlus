
(function(factory) {
	typeof define === 'function' && define.amd ? define(factory) :
		factory();
})((function() {
	'use strict';

	var themeStorageKey = 'appTheme';
	var defaultTheme = 'light';
	var selectedTheme;
	var params = new Proxy(new URLSearchParams(window.location.search), {
		get: function get(searchParams, prop) {
			return searchParams.get(prop);
		}
	});
	if (!!params.theme) {
		localStorage.setItem(themeStorageKey, params.theme);
		selectedTheme = params.theme;
	} else {
		var storedTheme = localStorage.getItem(themeStorageKey);
		selectedTheme = storedTheme ? storedTheme : defaultTheme;
	}
	document.body.classList.remove('theme-dark', 'theme-light');
	document.body.classList.add("theme-".concat(selectedTheme));

}));



(function(factory) {
	typeof define === 'function' && define.amd ? define(factory) :
		factory();
})((function() {
	'use strict';
//	var viewFluid = document.getElementById("viewFluid");
//	var viewBoxed = document.getElementById("viewBoxed");
	var viewStorageKey = 'appView';
	var defaultView = 'fluid';
	var selectedView;
	var params = new Proxy(new URLSearchParams(window.location.search), {
		get: function get(searchParams, prop) {
			return searchParams.get(prop);
		}
	});
	if (!!params.appView) {
		localStorage.setItem(viewStorageKey, params.appView);
		selectedView = params.appView;
	} else {
		var storedView = localStorage.getItem(viewStorageKey);
		//alert(storedView);
		selectedView = storedView ? storedView : defaultView;
	}
	document.body.classList.remove('layout-fluid', 'layout-boxed');
	document.body.classList.add("layout-".concat(selectedView));
	
//	var theViewKey = localStorage.getItem(viewStorageKey);
//	if (theViewKey === 'fluid') {
//		viewBoxed.style.visibility == 'visible' ? 'hidden' : 'visible';
//	} else {
//		viewFluid.style.visibility == 'visible' ? 'hidden' : 'visible';
//	}

}));


(function(factory) {
	typeof define === 'function' && define.amd ? define(factory) :
		factory();
})((function() {
	'use strict';

	function _slicedToArray(arr, i) {
		return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest();
	}
	function _arrayWithHoles(arr) {
		if (Array.isArray(arr)) return arr;
	}
	function _iterableToArrayLimit(arr, i) {
		var _i = arr == null ? null : typeof Symbol !== "undefined" && arr[Symbol.iterator] || arr["@@iterator"];
		if (_i == null) return;
		var _arr = [];
		var _n = true;
		var _d = false;
		var _s, _e;
		try {
			for (_i = _i.call(arr); !(_n = (_s = _i.next()).done); _n = true) {
				_arr.push(_s.value);
				if (i && _arr.length === i) break;
			}
		} catch (err) {
			_d = true;
			_e = err;
		} finally {
			try {
				if (!_n && _i["return"] != null) _i["return"]();
			} finally {
				if (_d) throw _e;
			}
		}
		return _arr;
	}
	function _unsupportedIterableToArray(o, minLen) {
		if (!o) return;
		if (typeof o === "string") return _arrayLikeToArray(o, minLen);
		var n = Object.prototype.toString.call(o).slice(8, -1);
		if (n === "Object" && o.constructor) n = o.constructor.name;
		if (n === "Map" || n === "Set") return Array.from(o);
		if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen);
	}
	function _arrayLikeToArray(arr, len) {
		if (len == null || len > arr.length) len = arr.length;
		for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i];
		return arr2;
	}
	function _nonIterableRest() {
		throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.");
	}

	var items = {
		'menu-position': {
			localStorage: 'appMenuPosition',
			default: 'top'
		},
		'menu-behavior': {
			localStorage: 'appMenuBehavior',
			default: 'sticky'
		},
		'container-layout': {
			localStorage: 'appContainerLayout',
			default: 'boxed'
		}
	};
	var config = {};
	for (var _i = 0, _Object$entries = Object.entries(items); _i < _Object$entries.length; _i++) {
		var _Object$entries$_i = _slicedToArray(_Object$entries[_i], 2),
			key = _Object$entries$_i[0],
			params = _Object$entries$_i[1];
		var lsParams = localStorage.getItem(params.localStorage);
		config[key] = lsParams ? lsParams : params.default;
	}
	var parseUrl = function parseUrl() {
		var search = window.location.search.substring(1);
		var params = search.split('&');
		for (var i = 0; i < params.length; i++) {
			var arr = params[i].split('=');
			var _key = arr[0];
			var value = arr[1];
			if (!!items[_key]) {
				localStorage.setItem(items[_key].localStorage, value);
				config[_key] = value;
			}
		}
	};
	var toggleFormControls = function toggleFormControls(form) {
		for (var _i2 = 0, _Object$entries2 = Object.entries(items); _i2 < _Object$entries2.length; _i2++) {
			var _Object$entries2$_i = _slicedToArray(_Object$entries2[_i2], 2),
				_key2 = _Object$entries2$_i[0];
			_Object$entries2$_i[1];
			var elem = form.querySelector("[name=\"settings-".concat(_key2, "\"][value=\"").concat(config[_key2], "\"]"));
			if (elem) {
				elem.checked = true;
			}
		}
	};
	var submitForm = function submitForm(form) {
		for (var _i3 = 0, _Object$entries3 = Object.entries(items); _i3 < _Object$entries3.length; _i3++) {
			var _Object$entries3$_i = _slicedToArray(_Object$entries3[_i3], 2),
				_key3 = _Object$entries3$_i[0],
				_params2 = _Object$entries3$_i[1];
			var value = form.querySelector("[name=\"settings-".concat(_key3, "\"]:checked")).value;
			localStorage.setItem(_params2.localStorage, value);
			config[_key3] = value;
		}
		window.dispatchEvent(new Event('resize'));
		new bootstrap.Offcanvas(form).hide();
	};
	parseUrl();
	var form = document.querySelector('#offcanvasSettings');
	if (form) {
		form.addEventListener('submit', function(e) {
			e.preventDefault();
			submitForm(form);
		});
		toggleFormControls(form);
	}

}));

