/**
 * ============================================================
 * Tienda Sara - Shared JavaScript
 * Cart management, filters, sorting, and UI interactions
 * ============================================================
 */

// ==================== CART STATE ====================
function getCart() {
    return JSON.parse(localStorage.getItem('tiendaSaraCart') || '[]');
}

function saveCart(cart) {
    localStorage.setItem('tiendaSaraCart', JSON.stringify(cart));
    updateCartBadge();
}

function updateCartBadge() {
    const cart = getCart();
    const total = cart.reduce((sum, item) => sum + item.qty, 0);
    document.querySelectorAll('#cartCount').forEach(el => {
        el.textContent = total;
        el.style.display = total > 0 ? 'flex' : 'none';
    });
}

// ==================== ADD TO CART ====================
function addToCart(id, name, price, img, brand) {
    const cart = getCart();
    const existing = cart.find(item => item.id === id);
    if (existing) {
        existing.qty += 1;
    } else {
        cart.push({ id, name, price, img, brand, qty: 1 });
    }
    saveCart(cart);
    showToast(`${name} agregado al carrito`);
}

// ==================== REMOVE / UPDATE ====================
function removeFromCart(id) {
    let cart = getCart().filter(item => item.id !== id);
    saveCart(cart);
    renderCart();
}

function updateQty(id, delta) {
    const cart = getCart();
    const item = cart.find(i => i.id === id);
    if (item) {
        item.qty = Math.max(1, item.qty + delta);
        saveCart(cart);
        renderCart();
    }
}

function setQty(id, val) {
    const cart = getCart();
    const item = cart.find(i => i.id === id);
    if (item) {
        item.qty = Math.max(1, parseInt(val) || 1);
        saveCart(cart);
        renderCart();
    }
}

// ==================== TOAST NOTIFICATION ====================
function showToast(message) {
    let container = document.getElementById('toastContainer');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toastContainer';
        container.className = 'position-fixed bottom-0 end-0 p-3';
        container.style.zIndex = '1100';
        document.body.appendChild(container);
    }

    const toastId = 'toast-' + Date.now();
    const toastHTML = `
        <div id="${toastId}" class="toast align-items-center text-bg-dark border-0 show" role="alert" style="border-radius:12px;">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="bi bi-check-circle-fill text-success me-2"></i>${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>`;
    container.insertAdjacentHTML('beforeend', toastHTML);

    setTimeout(() => {
        const el = document.getElementById(toastId);
        if (el) el.remove();
    }, 3000);
}

// ==================== FORMAT CURRENCY ====================
function formatMoney(num) {
    return '$' + num.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}

// ==================== RENDER CART PAGE ====================
function renderCart() {
    const cartContent = document.getElementById('cartContent');
    if (!cartContent) return;

    const cart = getCart();

    if (cart.length === 0) {
        cartContent.innerHTML = `
            <div class="empty-cart">
                <i class="bi bi-cart-x d-block"></i>
                <h4>Tu carrito está vacío</h4>
                <p class="text-muted mb-4">Agrega productos desde nuestra galería</p>
                <a href="productos.html" class="btn btn-sara"><span><i class="bi bi-grid me-2"></i>Ver Productos</span></a>
            </div>`;
        updateSummary(0);
        return;
    }

    let rows = '';
    cart.forEach(item => {
        const subtotal = item.price * item.qty;
        rows += `
        <div class="card mb-3" style="border-radius:var(--sara-radius);border:1px solid var(--sara-gray-200);">
            <div class="card-body">
                <div class="row align-items-center">
                    <div class="col-md-5">
                        <div class="cart-product-info">
                            <img src="${item.img}" alt="${item.name}" class="cart-product-img">
                            <div>
                                <h6>${item.name}</h6>
                                <small>${item.brand}</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-6 col-md-2 text-center mt-2 mt-md-0">
                        <small class="text-muted d-block d-md-none mb-1">Precio</small>
                        <span class="fw-600">${formatMoney(item.price)}</span>
                    </div>
                    <div class="col-6 col-md-2 text-center mt-2 mt-md-0">
                        <small class="text-muted d-block d-md-none mb-1">Cantidad</small>
                        <div class="qty-control mx-auto">
                            <button onclick="updateQty('${item.id}',-1)">−</button>
                            <input type="number" value="${item.qty}" min="1" onchange="setQty('${item.id}',this.value)">
                            <button onclick="updateQty('${item.id}',1)">+</button>
                        </div>
                    </div>
                    <div class="col-6 col-md-2 text-center mt-2 mt-md-0">
                        <small class="text-muted d-block d-md-none mb-1">Subtotal</small>
                        <span class="fw-bold" style="color:var(--sara-primary);">${formatMoney(subtotal)}</span>
                    </div>
                    <div class="col-6 col-md-1 text-center mt-2 mt-md-0">
                        <button class="btn-delete" onclick="removeFromCart('${item.id}')" title="Eliminar">
                            <i class="bi bi-trash3"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>`;
    });

    cartContent.innerHTML = rows;

    const subtotal = cart.reduce((s, i) => s + i.price * i.qty, 0);
    updateSummary(subtotal);
}

function updateSummary(subtotal) {
    const shipping = parseFloat(document.getElementById('shippingOption')?.value || 0);
    const tax = subtotal * 0.16;
    const discount = parseFloat(localStorage.getItem('tiendaSaraDiscount') || 0);
    const total = subtotal + tax + shipping - discount;

    const el = (id) => document.getElementById(id);
    if (el('summarySubtotal')) el('summarySubtotal').textContent = formatMoney(subtotal);
    if (el('summaryTax')) el('summaryTax').textContent = formatMoney(tax);
    if (el('summaryShipping')) el('summaryShipping').textContent = shipping > 0 ? formatMoney(shipping) : 'Gratis';
    if (el('summaryTotal')) el('summaryTotal').textContent = formatMoney(Math.max(0, total));

    if (el('discountRow')) {
        el('discountRow').style.display = discount > 0 ? 'flex' : 'none';
        if (el('summaryDiscount')) el('summaryDiscount').textContent = '-' + formatMoney(discount);
    }
}

// ==================== PRODUCT FILTERS & SORT ====================
function applyFilters() {
    const items = document.querySelectorAll('.product-item');
    if (!items.length) return;

    const checkedCats = [...document.querySelectorAll('.filter-cat:checked')].map(c => c.value);
    const checkedBrands = [...document.querySelectorAll('.filter-brand:checked')].map(c => c.value);
    const maxPrice = parseFloat(document.getElementById('priceRange')?.value || 99999);

    let visibleCount = 0;
    items.forEach(item => {
        const cat = item.dataset.category;
        const brand = item.dataset.brand;
        const price = parseFloat(item.dataset.price);

        const catMatch = checkedCats.length === 0 || checkedCats.includes(cat);
        const brandMatch = checkedBrands.length === 0 || checkedBrands.includes(brand);
        const priceMatch = price <= maxPrice;

        if (catMatch && brandMatch && priceMatch) {
            item.style.display = '';
            visibleCount++;
        } else {
            item.style.display = 'none';
        }
    });

    const countEl = document.getElementById('productCount');
    if (countEl) countEl.textContent = visibleCount;
}

function applySorting() {
    const grid = document.getElementById('productGrid');
    if (!grid) return;

    const sortVal = document.getElementById('sortSelect')?.value;
    const items = [...grid.querySelectorAll('.product-item')];

    items.sort((a, b) => {
        switch (sortVal) {
            case 'price-asc':
                return parseFloat(a.dataset.price) - parseFloat(b.dataset.price);
            case 'price-desc':
                return parseFloat(b.dataset.price) - parseFloat(a.dataset.price);
            case 'popularity':
                return parseInt(b.dataset.popularity) - parseInt(a.dataset.popularity);
            default:
                return 0;
        }
    });

    items.forEach(item => grid.appendChild(item));
}

// ==================== SEARCH ====================
function handleSearch() {
    const query = document.getElementById('searchInput')?.value.toLowerCase().trim();
    if (!query) return;
    window.location.href = 'productos.html?search=' + encodeURIComponent(query);
}

function applySearchFromURL() {
    const params = new URLSearchParams(window.location.search);
    const search = params.get('search')?.toLowerCase();
    if (!search) return;

    document.querySelectorAll('.product-item').forEach(item => {
        const name = item.dataset.name?.toLowerCase() || '';
        const brand = item.dataset.brand?.toLowerCase() || '';
        if (!name.includes(search) && !brand.includes(search)) {
            item.style.display = 'none';
        }
    });

    const input = document.getElementById('searchInput');
    if (input) input.value = params.get('search');
}

// ==================== PRODUCT MODAL ====================
function setupProductModal() {
    const modal = document.getElementById('productModal');
    if (!modal) return;

    modal.addEventListener('show.bs.modal', function (event) {
        const btn = event.relatedTarget;
        document.getElementById('modalImg').src = btn.dataset.img;
        document.getElementById('modalName').textContent = btn.dataset.name;
        document.getElementById('modalBrand').textContent = btn.dataset.brand;
        document.getElementById('modalPrice').textContent = btn.dataset.price;
        document.getElementById('modalDesc').textContent = btn.dataset.desc;

        const addBtn = document.getElementById('modalAddCart');
        addBtn.onclick = function () {
            const priceNum = parseFloat(btn.dataset.price.replace(/[$,]/g, ''));
            const imgThumb = btn.dataset.img.replace('w=600', 'w=100');
            addToCart(
                'modal-' + btn.dataset.name.toLowerCase().replace(/\s/g, '-'),
                btn.dataset.name,
                priceNum,
                imgThumb,
                btn.dataset.brand
            );
            bootstrap.Modal.getInstance(modal).hide();
        };
    });
}

// ==================== COUPON SYSTEM ====================
function setupCoupon() {
    const btn = document.getElementById('applyCoupon');
    if (!btn) return;

    btn.addEventListener('click', function () {
        const code = document.getElementById('couponInput').value.trim().toUpperCase();
        const msg = document.getElementById('couponMsg');
        const coupons = { 'SARA10': 0.10, 'BIENVENIDO': 0.05, 'DESCUENTO20': 0.20 };

        if (coupons[code]) {
            const cart = getCart();
            const subtotal = cart.reduce((s, i) => s + i.price * i.qty, 0);
            const discount = subtotal * coupons[code];
            localStorage.setItem('tiendaSaraDiscount', discount.toString());
            msg.innerHTML = `<span class="text-success"><i class="bi bi-check-circle me-1"></i>Cupón "${code}" aplicado (${coupons[code] * 100}% desc.)</span>`;
            renderCart();
        } else {
            localStorage.setItem('tiendaSaraDiscount', '0');
            msg.innerHTML = '<span class="text-danger"><i class="bi bi-x-circle me-1"></i>Cupón no válido</span>';
            renderCart();
        }
    });
}

// ==================== CATEGORY URL FILTER ====================
function applyCategoryFromURL() {
    const params = new URLSearchParams(window.location.search);
    const cat = params.get('cat');
    if (!cat) return;

    const checkbox = document.querySelector(`.filter-cat[value="${cat}"]`);
    if (checkbox) {
        checkbox.checked = true;
        applyFilters();
    }
}

// ==================== CHECKOUT ====================
function setupCheckout() {
    const btn = document.getElementById('checkoutBtn');
    if (!btn) return;

    btn.addEventListener('click', function () {
        const cart = getCart();
        if (cart.length === 0) {
            showToast('Tu carrito está vacío');
            return;
        }
        showToast('¡Gracias por tu compra! Redirigiendo al pago...');
    });
}

// ==================== INIT ====================
document.addEventListener('DOMContentLoaded', function () {
    updateCartBadge();
    renderCart();
    setupProductModal();
    setupCoupon();
    setupCheckout();
    applyCategoryFromURL();
    applySearchFromURL();

    // Filter listeners
    document.querySelectorAll('.filter-cat, .filter-brand').forEach(cb => {
        cb.addEventListener('change', applyFilters);
    });

    const priceRange = document.getElementById('priceRange');
    if (priceRange) {
        priceRange.addEventListener('input', function () {
            document.getElementById('priceValue').textContent = formatMoney(parseFloat(this.value));
            applyFilters();
        });
    }

    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) sortSelect.addEventListener('change', applySorting);

    const clearBtn = document.getElementById('clearFilters');
    if (clearBtn) {
        clearBtn.addEventListener('click', function () {
            document.querySelectorAll('.filter-cat, .filter-brand').forEach(cb => cb.checked = false);
            if (priceRange) { priceRange.value = 20000; document.getElementById('priceValue').textContent = '$20,000'; }
            applyFilters();
        });
    }

    // Search
    const searchBtn = document.getElementById('searchBtn');
    if (searchBtn) searchBtn.addEventListener('click', handleSearch);
    const searchInput = document.getElementById('searchInput');
    if (searchInput) searchInput.addEventListener('keypress', e => { if (e.key === 'Enter') handleSearch(); });

    // Shipping option change
    const shippingOption = document.getElementById('shippingOption');
    if (shippingOption) {
        shippingOption.addEventListener('change', function () {
            const cart = getCart();
            const subtotal = cart.reduce((s, i) => s + i.price * i.qty, 0);
            updateSummary(subtotal);
        });
    }
});
