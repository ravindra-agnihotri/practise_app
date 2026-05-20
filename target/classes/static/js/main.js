// ============================================
//   Automation Practice App - Main JS
// ============================================

document.addEventListener('DOMContentLoaded', () => {
    initModals();
    initTabs();
    initAlerts();
    initNavHighlight();
    initTooltips();
    initDataTables();
});

// ===== MODALS =====
function initModals() {
    // Open modal
    document.querySelectorAll('[data-modal-target]').forEach(btn => {
        btn.addEventListener('click', () => {
            const targetId = btn.getAttribute('data-modal-target');
            const modal = document.getElementById(targetId);
            if (modal) openModal(modal);
        });
    });

    // Close modal via backdrop click
    document.querySelectorAll('.modal-backdrop').forEach(backdrop => {
        backdrop.addEventListener('click', (e) => {
            if (e.target === backdrop) closeModal(backdrop);
        });
    });

    // Close modal via close button
    document.querySelectorAll('.modal-close, [data-modal-close]').forEach(btn => {
        btn.addEventListener('click', () => {
            const modal = btn.closest('.modal-backdrop');
            if (modal) closeModal(modal);
        });
    });

    // Close modal on Escape key
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            document.querySelectorAll('.modal-backdrop.active').forEach(closeModal);
        }
    });
}

function openModal(modal) {
    modal.classList.add('active');
    document.body.style.overflow = 'hidden';
}

function closeModal(modal) {
    modal.classList.remove('active');
    document.body.style.overflow = '';
}

// ===== TABS =====
function initTabs() {
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const tabGroup = btn.getAttribute('data-tab-group');
            const tabId = btn.getAttribute('data-tab');

            // Deactivate all in group
            document.querySelectorAll(`[data-tab-group="${tabGroup}"]`).forEach(t => t.classList.remove('active'));
            document.querySelectorAll(`[data-tab-content="${tabGroup}"]`).forEach(c => c.classList.remove('active'));

            // Activate selected
            btn.classList.add('active');
            const content = document.querySelector(`[data-tab-content="${tabGroup}"][data-tab="${tabId}"]`);
            if (content) content.classList.add('active');
        });
    });
}

// ===== AUTO-DISMISS ALERTS =====
function initAlerts() {
    document.querySelectorAll('.alert[data-auto-dismiss]').forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-10px)';
            alert.style.transition = 'all 0.3s';
            setTimeout(() => alert.remove(), 300);
        }, parseInt(alert.getAttribute('data-auto-dismiss') || '4000'));
    });

    document.querySelectorAll('.alert-close').forEach(btn => {
        btn.addEventListener('click', () => {
            const alert = btn.closest('.alert');
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 200);
        });
    });
}

// ===== NAV HIGHLIGHT =====
function initNavHighlight() {
    const current = window.location.pathname;
    document.querySelectorAll('.navbar-nav .nav-link').forEach(link => {
        if (link.getAttribute('href') === current) {
            link.classList.add('active');
        }
    });
}

// ===== TOOLTIPS =====
function initTooltips() {
    document.querySelectorAll('[data-tooltip]').forEach(el => {
        el.style.position = 'relative';
        el.addEventListener('mouseenter', () => {
            const tip = document.createElement('div');
            tip.className = 'tooltip-popup';
            tip.textContent = el.getAttribute('data-tooltip');
            tip.style.cssText = `
                position:absolute; bottom:calc(100% + 6px); left:50%;
                transform:translateX(-50%); background:#1e293b;
                border:1px solid #334155; color:#e2e8f0;
                padding:4px 10px; border-radius:6px; font-size:0.75rem;
                white-space:nowrap; z-index:9999; pointer-events:none;
            `;
            el.appendChild(tip);
        });
        el.addEventListener('mouseleave', () => {
            el.querySelector('.tooltip-popup')?.remove();
        });
    });
}

// ===== DATA TABLES =====
function initDataTables() {
    const searchInputs = document.querySelectorAll('[data-table-search]');
    searchInputs.forEach(input => {
        const tableId = input.getAttribute('data-table-search');
        const table = document.getElementById(tableId);
        if (!table) return;

        input.addEventListener('input', () => {
            const query = input.value.toLowerCase();
            table.querySelectorAll('tbody tr').forEach(row => {
                const text = row.textContent.toLowerCase();
                row.style.display = text.includes(query) ? '' : 'none';
            });
        });
    });
}

// ===== CONFIRMATION DIALOGS =====
function confirmAction(message, formId) {
    if (confirm(message || 'Are you sure?')) {
        if (formId) document.getElementById(formId).submit();
        return true;
    }
    return false;
}

// ===== NOTIFICATION TOAST =====
function showToast(message, type = 'success') {
    const container = document.getElementById('toast-container') || createToastContainer();
    const toast = document.createElement('div');
    toast.className = `alert alert-${type}`;
    toast.style.cssText = `
        margin-bottom:8px; min-width:280px; max-width:400px;
        box-shadow:0 8px 24px rgba(0,0,0,0.4);
        animation: slideIn 0.3s ease;
    `;

    const icons = { success: '✓', danger: '✕', warning: '⚠', info: 'ℹ' };
    toast.innerHTML = `<span>${icons[type] || '•'}</span><span>${message}</span>`;
    container.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(100%)';
        toast.style.transition = 'all 0.3s';
        setTimeout(() => toast.remove(), 300);
    }, 4000);
}

function createToastContainer() {
    const container = document.createElement('div');
    container.id = 'toast-container';
    container.style.cssText = `
        position:fixed; top:80px; right:24px; z-index:9999;
        display:flex; flex-direction:column; gap:8px;
    `;
    document.body.appendChild(container);
    return container;
}

// ===== RANGE SLIDER =====
document.querySelectorAll('.form-range').forEach(range => {
    const output = document.getElementById(range.id + '_value');
    if (output) {
        output.textContent = range.value;
        range.addEventListener('input', () => { output.textContent = range.value; });
    }
});

// ===== CHAR COUNTER =====
document.querySelectorAll('textarea[maxlength]').forEach(ta => {
    const max = parseInt(ta.getAttribute('maxlength'));
    const counter = document.createElement('div');
    counter.style.cssText = 'text-align:right; font-size:0.75rem; color:#94a3b8; margin-top:4px;';
    counter.textContent = `0 / ${max}`;
    ta.parentNode.insertBefore(counter, ta.nextSibling);
    ta.addEventListener('input', () => {
        counter.textContent = `${ta.value.length} / ${max}`;
    });
});

// ===== API TESTER =====
async function testApi(method, url, body = null) {
    const outputEl = document.getElementById('api-response');
    if (outputEl) {
        outputEl.textContent = 'Loading...';
    }
    try {
        const opts = {
            method,
            headers: { 'Content-Type': 'application/json' }
        };
        if (body) opts.body = JSON.stringify(body);
        const res = await fetch(url, opts);
        const data = await res.json();
        const result = JSON.stringify(data, null, 2);
        if (outputEl) {
            outputEl.textContent = `HTTP ${res.status} ${res.statusText}\n\n${result}`;
        }
        return data;
    } catch (e) {
        if (outputEl) outputEl.textContent = 'Error: ' + e.message;
    }
}
