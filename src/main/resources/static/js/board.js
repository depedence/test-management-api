let cards = []
let activeId = null
let dragId = null
let originalSnapshot = null

const STATUS_LABEL = {
    BUG: 'Bug',
    FEATURE: 'Feature',
    TESTCASE: 'Test Case'
}

async function api(method, path, body) {
    const res = await fetch('/api/card' + path, {
        method,
        headers: body ? { 'Content-Type': 'application/json' } : {},
        body: body ? JSON.stringify(body) : undefined
    })

    if (!res.ok) {
        const err = await res.json().catch(() => ({}))
        throw new Error(err.error || 'Ошибка сервера')
    }

    return res.status === 204 ? null : res.json()
}

function esc(s) {
    return String(s)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
}

function toast(msg, type = 'success') {
    const container = document.getElementById('toast-container')

    const el = document.createElement('div')
    el.className = `toast toast-${type}`
    el.textContent = msg
    container.appendChild(el)

    requestAnimationFrame(() => el.classList.add('show'))

    setTimeout(() => {
        el.classList.remove('show')
        el.addEventListener('transitionend', () => el.remove())
    }, 3000)
}

function closeAll() {
    document.querySelectorAll('.overlay').forEach(o => o.classList.remove('open'))
    activeId = null
    originalSnapshot = null
}

function getFormSnapshot() {
    return {
        title: document.getElementById('f-title').value.trim(),
        content: document.getElementById('f-content').value.trim(),
        status: document.getElementById('f-status').value
    }
}

function isFormValid() {
    return document.getElementById('f-title').value.trim().length > 0
}

function isFormDirty() {
    if (!originalSnapshot) return true
    const current = getFormSnapshot()
    return (
        current.title !== originalSnapshot.title ||
        current.content !== originalSnapshot.content ||
        current.status !== originalSnapshot.status
    )
}

function onFormInput() {
    const btn = document.getElementById('btn-submit')
    btn.disabled = !isFormDirty() || !isFormValid()
}

function render() {
    ['BUG', 'FEATURE', 'TESTCASE'].forEach(status => {
        const col = cards.filter(c => c.status === status)
        document.getElementById('count-' + status).textContent = col.length

        const el = document.getElementById('col-' + status)

        if (!col.length) {
            el.innerHTML = '<div class="empty">Нет карточек</div>'
            return
        }

        el.innerHTML = col.map(c => `
            <div class="card"
                 draggable="true"
                 data-id="${c.id}"
                 onclick="openEdit(${c.id})">
                <button class="btn-trash" onclick="event.stopPropagation(); confirmDelete(${c.id})" title="Удалить">
                    <svg width="14" height="14" viewBox="0 0 14 14" fill="none">
                        <path d="M1 3h12M5 3V2h4v1M2 3l1 9h6l1-9H2z" stroke="currentColor" stroke-width="1.4" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                </button>
                <div class="card-title">${esc(c.title)}</div>
                ${c.content ? `<div class="card-content">${esc(c.content)}</div>` : ''}
                <div class="card-footer">
                    <span class="badge badge-${c.status}">${STATUS_LABEL[c.status]}</span>
                </div>
            </div>
        `).join('')

        el.querySelectorAll('.card').forEach(cardEl => {
            cardEl.addEventListener('dragstart', onDragStart)
            cardEl.addEventListener('dragend', onDragEnd)
        })
    })
}

async function loadCards() {
    try {
        cards = await api('GET', '')
        render()
    } catch (e) {
        toast(e.message, 'error')
    }
}

function openCreate() {
    activeId = null
    originalSnapshot = null

    document.getElementById('form-title').textContent = 'Новая карточка'
    document.getElementById('f-title').value = ''
    document.getElementById('f-content').value = ''
    document.getElementById('f-status').value = 'BUG'
    document.getElementById('btn-delete').style.display = 'none'

    const btn = document.getElementById('btn-submit')
    btn.textContent = 'Создать'
    btn.disabled = false

    document.getElementById('overlay-form').classList.add('open')
}

function openEdit(id) {
    const card = cards.find(c => c.id === id)
    if (!card) return

    activeId = id
    originalSnapshot = {
        title: card.title,
        content: card.content || '',
        status: card.status
    }

    document.getElementById('form-title').textContent = 'Редактировать'
    document.getElementById('f-title').value = card.title
    document.getElementById('f-content').value = card.content || ''
    document.getElementById('f-status').value = card.status
    document.getElementById('btn-delete').style.display = 'inline-flex'

    const btn = document.getElementById('btn-submit')
    btn.textContent = 'Сохранить'
    btn.disabled = true

    document.getElementById('overlay-form').classList.add('open')
}

async function submitForm() {
    const { title, content, status } = getFormSnapshot()

    if (!title) {
        toast('Введите заголовок', 'error')
        return
    }

    try {
        if (activeId) {
            await api('PUT', '/' + activeId, { title, content, status })
            toast('Карточка обновлена', 'warning')
        } else {
            await api('POST', '', { title, content, status })
            toast('Карточка создана', 'success')
        }
        closeAll()
        loadCards()
    } catch (e) {
        toast(e.message, 'error')
    }
}

async function deleteCard() {
    if (!activeId) return
    try {
        await api('DELETE', '/' + activeId)
        toast('Карточка удалена', 'error')
        closeAll()
        loadCards()
    } catch (e) {
        toast(e.message, 'error')
    }
}

async function confirmDelete(id) {
    activeId = id
    await deleteCard()
}

function onDragStart(e) {
    dragId = parseInt(e.currentTarget.dataset.id)
    e.currentTarget.classList.add('dragging')
    e.dataTransfer.effectAllowed = 'move'
}

function onDragEnd(e) {
    e.currentTarget.classList.remove('dragging')
    document.querySelectorAll('.column-cards').forEach(col => col.classList.remove('drag-over'))
    dragId = null
}

document.querySelectorAll('.column-cards').forEach(col => {
    col.addEventListener('dragover', e => {
        e.preventDefault()
        e.dataTransfer.dropEffect = 'move'
        document.querySelectorAll('.column-cards').forEach(c => c.classList.remove('drag-over'))
        col.classList.add('drag-over')
    })

    col.addEventListener('dragleave', e => {
        if (!col.contains(e.relatedTarget)) col.classList.remove('drag-over')
    })

    col.addEventListener('drop', async e => {
        e.preventDefault()
        col.classList.remove('drag-over')

        if (dragId === null) return

        const newStatus = col.closest('.column').dataset.col
        const card = cards.find(c => c.id === dragId)

        if (!card || card.status === newStatus) return

        try {
            await api('PUT', '/' + dragId, {
                title: card.title,
                content: card.content || '',
                status: newStatus
            })
            toast(`Перемещено в ${STATUS_LABEL[newStatus]}`, 'warning')
            loadCards()
        } catch (err) {
            toast(err.message, 'error')
        }
    })
})

async function logout() {
    await fetch('/logout', { method: 'POST' }).catch(() => {})
    window.location.href = '/login'
}

document.querySelectorAll('.overlay').forEach(overlay => {
    overlay.addEventListener('click', e => {
        if (e.target === overlay) closeAll()
    })
})

loadCards()