var $ = document.querySelector.bind(document);
var $$ = document.querySelectorAll.bind(document);


var wrapper = $('#wrapper');
var boxTool = $('#box-tool');
var btnExportHtml = $('#export-html');
var showSource = $('#show-source');

let temp;

const handleDragStart = (e) => {
	e.dataTransfer.setData('text', e.target.id);
	temp = e.target.id;
	return false;
}

const handleDragEnd = (e) => {
	e.target.classList.remove('active_custom')
	e.target.classList.remove('box-hover')
	e.dataTransfer.clearData();
}


const handleDragOver = (e) => {
	e.preventDefault();
	// if (temp !== e.target.id) {
	// 	$(`#${e.target.id}`).classList.add('box-hover')
	// }
}

const handleDragLeave = (e) => {
	// $(`#${e.target.id}`).classList.remove('box-hover')
}

const handleDrop = (e) => {
	e.preventDefault();
	var id = e.dataTransfer.getData('text');
	var cloneNode = $(`#${id}`).cloneNode(true);
	cloneNode.id = id + '_' + new Date().getTime();
	cloneNode.innerHTML = dataBootstrap.find(dt => dt.id === id).content;
	// cloneNode.classList.add('draggable');

	cloneNode.firstElementChild.classList.add('draggable');
	// cloneNode.firstElementChild.setAttribute('readonly', 'readonly');
	cloneNode.style.position = 'absolute';
	
	e.target.appendChild(cloneNode.firstElementChild);
	cloneNode.style.left = e.clientX;
	cloneNode.style.top = e.clientY;
	$(`#${e.target.id}`).classList.remove('box-hover')
}


const parseStringToNumber = (str) => {
	return str.substr(0, str.length - 2);
}

const handleCLickExportHtml = () => {
	$('#show-source').innerText = $(`#wrapper`).outerHTML;
}




/*Make move obj */
let isDragging = false;
let isResizing = false;
let isScaling = false;
let isRotating = false;
let rotation = 0;
let scale = 1;
let subObj;
let obj;
let shiftX;
let shiftY;
let originX;
let originY;
let originW;
let originH;
let originT;
let originL;
let originB;
let originR;

function moveAction(wrapper) {
	wrapper.onmousedown = e => {
		isDragging = true;
		originX = e.pageX;
		originY = e.pageY;
		var objectActions = Array.from($$(`.object-action`));
		subObj = objectActions.find(action => action.id === e.target.id);
		if (subObj) {
			if (subObj.classList.contains('object-scale-tl') 
			|| subObj.classList.contains('object-scale-tr') 
			|| subObj.classList.contains('object-scale-bl') 
			|| subObj.classList.contains('object-scale-br')) {
				isScaling = true;
			}
			if (subObj.classList.contains('object-resize-t') 
			|| subObj.classList.contains('object-resize-l') 
			|| subObj.classList.contains('object-resize-b') 
			|| subObj.classList.contains('object-resize-r')) {
				isResizing = true;
			}
			if (subObj.classList.contains('object-rotate-point-t') 
			|| subObj.classList.contains('object-rotate-point-b') 
			|| subObj.classList.contains('object-rotate-point-l') 
			|| subObj.classList.contains('object-rotate-point-r')) {
				isRotating = true;
				isDragging = false;
			}
			originW = obj.getBoundingClientRect().width;
			originH = obj.getBoundingClientRect().height;
			originT = obj.getBoundingClientRect().top;
			originL = obj.getBoundingClientRect().left;
			originB = obj.getBoundingClientRect().bottom;
			originR = obj.getBoundingClientRect().right;
		}
		if (obj && !subObj) {
			handleBlurObject(obj);
		}
		obj = e.target.closest('.draggable');
		if (!obj) {
			return;
		}
		shiftX = e.clientX - obj.getBoundingClientRect().left;
		shiftY = e.clientY - obj.getBoundingClientRect().top;
		handleActiveObject(obj);
	}
	document.onmousemove = e => {
		moveObject(e);
		resizeObject(e);
		scaleObject(e);
		rotateObject(e);
	}
	wrapper.onmouseup = e => {
		isDragging = false;
		isResizing = false;
		isScaling = false;
		isRotating = false;
		subObj = undefined;
	}
}

function moveObject(e) {
	if (obj && isDragging) {
		obj.style.left = e.clientX - shiftX + 'px';
		obj.style.top = e.clientY - shiftY + 'px';
		checkOutsideFrame(obj);
	}
}

function resizeObject(e) {
	if (obj && isResizing && subObj) {
		if (subObj.closest('.object-resize-l')) {
			obj.style.left = originX - (originX - e.pageX) + 'px';
			obj.style.width = originW + (originX - e.pageX) + 'px';
			obj.style.top = originT + 'px';
			if (originL - wrapper.getBoundingClientRect().left + originW <= originW + (originX - e.pageX)) {
				obj.style.width = originL - wrapper.getBoundingClientRect().left + originW + 'px';
			}
		}
		if (subObj.closest('.object-resize-r')) {
			obj.style.left = originX - originW + 'px';
			obj.style.width = originW + (e.pageX - originX) + 'px';
			obj.style.top = originT + 'px';
			if (wrapper.getBoundingClientRect().right - originL <= originW + (e.pageX - originX)) {
				obj.style.width = wrapper.getBoundingClientRect().right - originL + 'px';
			}
		}
		if (subObj.closest('.object-resize-t')) {
			obj.style.top = originY - (originY - e.pageY) + 'px';
			obj.style.height = originH + (originY - e.pageY) + 'px';
			obj.style.left = originL + 'px';
			if (originT - wrapper.getBoundingClientRect().top + originH <= originH + (originY - e.pageY)) {
				obj.style.height = originT - wrapper.getBoundingClientRect().top + originH + 'px';
			}
		}
		if (subObj.closest('.object-resize-b')) {
			obj.style.top = originY - originH + 'px';
			obj.style.height = originH + (e.pageY - originY) + 'px';
			obj.style.left = originL + 'px';
			if (wrapper.getBoundingClientRect().bottom - originT <= originH + (e.pageY - originY)) {
				obj.style.height = wrapper.getBoundingClientRect().bottom - originT + 'px';
			}
		}
		checkOutsideFrame(obj);
	}
}

function scaleObject(e) {
	if (obj && isScaling && subObj) {

		if (subObj.closest('.object-scale-tl')) {
		}
		if (subObj.closest('.object-scale-tr')) {
		}
		if (subObj.closest('.object-scale-bl')) {
		}
		if (subObj.closest('.object-scale-br')) {
		}
		checkOutsideFrame(obj);
	}
}



function rotateObject(e) {
	const calcAngleDegrees = (x, y) => {
		return Math.atan2(x, y) * 180 / Math.PI;
	}
	if (obj && isRotating && subObj) {
		var degree = calcAngleDegrees(
			e.pageY - obj.getBoundingClientRect().top - (obj.getBoundingClientRect().height / 2), 
			e.pageX - obj.getBoundingClientRect().left - (obj.getBoundingClientRect().width / 2));
		rotation = degree;
		if (subObj.closest('.object-rotate-point-r')) {
			obj.style.transform = `rotate(${degree}deg)`;
		}
		if (subObj.closest('.object-rotate-point-l')) {
			obj.style.transform = `rotate(${degree + 180}deg)`;
		}
		if (subObj.closest('.object-rotate-point-t')) {
			obj.style.transform = `rotate(${degree + 90}deg)`;
		}
		if (subObj.closest('.object-rotate-point-b')) {
			obj.style.transform = `rotate(${degree - 90}deg)`;
		}
	}
	// checkOutsideFrame(obj);
}

function checkOutsideFrame(obj) {
	if (obj.getBoundingClientRect().left <= wrapper.getBoundingClientRect().left) {
		obj.style.left = wrapper.getBoundingClientRect().left + 'px';
	}
	if (obj.getBoundingClientRect().top <= wrapper.getBoundingClientRect().top) {
		obj.style.top = wrapper.getBoundingClientRect().top + 'px';
	}
	if (obj.getBoundingClientRect().right >= wrapper.getBoundingClientRect().right) {
		obj.style.left = wrapper.getBoundingClientRect().right - obj.getBoundingClientRect().width + 'px';
	}
	if (obj.getBoundingClientRect().bottom >= wrapper.getBoundingClientRect().bottom) {
		obj.style.top = wrapper.getBoundingClientRect().bottom - obj.getBoundingClientRect().height + 'px';
	}
}

moveAction(wrapper);

function handleActiveObject(object) {
	if ($(`#${object.id}-scale-tl`)) {
		return;
	}
	object.classList.add('object-active', 'draggable');
	var divScaleTl = document.createElement('div');
	var divScaleTr = document.createElement('div');
	var divScaleBl = document.createElement('div');
	var divScaleBr = document.createElement('div');
	var divResizeT = document.createElement('div');
	var divResizeL = document.createElement('div');
	var divResizeB = document.createElement('div');
	var divResizeR = document.createElement('div');
	var divRotateT = document.createElement('div');
	var divRotateB = document.createElement('div');
	var divRotateL = document.createElement('div');
	var divRotateR = document.createElement('div');
	var divRotatePointT = document.createElement('div');
	var divRotatePointB = document.createElement('div');
	var divRotatePointL = document.createElement('div');
	var divRotatePointR = document.createElement('div');

	divScaleTl.id = `${object.id}-scale-tl`;
	divScaleTr.id = `${object.id}-scale-tr`;
	divScaleBl.id = `${object.id}-scale-bl`;
	divScaleBr.id = `${object.id}-scale-br`;
	divResizeT.id = `${object.id}-resize-t`;
	divResizeL.id = `${object.id}-resize-l`;
	divResizeB.id = `${object.id}-resize-b`;
	divResizeR.id = `${object.id}-resize-r`;
	divRotateT.id = `${object.id}-rotate-t`;
	divRotateB.id = `${object.id}-rotate-b`;
	divRotateL.id = `${object.id}-rotate-l`;
	divRotateR.id = `${object.id}-rotate-r`;
	divRotatePointT.id = `${object.id}-rotate-point-t`;
	divRotatePointB.id = `${object.id}-rotate-point-b`;
	divRotatePointL.id = `${object.id}-rotate-point-l`;
	divRotatePointR.id = `${object.id}-rotate-point-r`;

	divScaleTl.classList.add('object-action', 'object-scale-tl');
	divScaleTr.classList.add('object-action', 'object-scale-tr');
	divScaleBl.classList.add('object-action', 'object-scale-bl');
	divScaleBr.classList.add('object-action', 'object-scale-br');
	divResizeT.classList.add('object-action', 'object-resize-t');
	divResizeL.classList.add('object-action', 'object-resize-l');
	divResizeB.classList.add('object-action', 'object-resize-b');
	divResizeR.classList.add('object-action', 'object-resize-r');
	divRotateT.classList.add('object-action', 'object-rotate-t');
	divRotateB.classList.add('object-action', 'object-rotate-b');
	divRotateL.classList.add('object-action', 'object-rotate-l');
	divRotateR.classList.add('object-action', 'object-rotate-r');
	divRotatePointT.classList.add('object-action', 'object-rotate-point-t');
	divRotatePointB.classList.add('object-action', 'object-rotate-point-b');
	divRotatePointL.classList.add('object-action', 'object-rotate-point-l');
	divRotatePointR.classList.add('object-action', 'object-rotate-point-r');

	object.appendChild(divScaleTl);
	object.appendChild(divScaleTr);
	object.appendChild(divScaleBl);
	object.appendChild(divScaleBr);
	object.appendChild(divResizeT);
	object.appendChild(divResizeL);
	object.appendChild(divResizeB);
	object.appendChild(divResizeR);
	object.appendChild(divRotateT);
	object.appendChild(divRotateB);
	object.appendChild(divRotateL);
	object.appendChild(divRotateR);
	object.appendChild(divRotatePointT);
	object.appendChild(divRotatePointB);
	object.appendChild(divRotatePointL);
	object.appendChild(divRotatePointR);
}

function handleBlurObject(object) {
	object.removeChild($(`#${object.id}-scale-tl`));
	object.removeChild($(`#${object.id}-scale-tr`));
	object.removeChild($(`#${object.id}-scale-bl`));
	object.removeChild($(`#${object.id}-scale-br`));
	object.removeChild($(`#${object.id}-resize-t`));
	object.removeChild($(`#${object.id}-resize-l`));
	object.removeChild($(`#${object.id}-resize-b`));
	object.removeChild($(`#${object.id}-resize-r`));
	object.removeChild($(`#${object.id}-rotate-t`));
	object.removeChild($(`#${object.id}-rotate-b`));
	object.removeChild($(`#${object.id}-rotate-l`));
	object.removeChild($(`#${object.id}-rotate-r`));
	object.removeChild($(`#${object.id}-rotate-point-t`));
	object.removeChild($(`#${object.id}-rotate-point-b`));
	object.removeChild($(`#${object.id}-rotate-point-l`));
	object.removeChild($(`#${object.id}-rotate-point-r`));
	object.classList.remove('object-active');
}


document.addEventListener('keydown', e => {
	if (e.key === 'Delete' && obj) {
		obj.remove();
		obj = undefined;
	}
})

