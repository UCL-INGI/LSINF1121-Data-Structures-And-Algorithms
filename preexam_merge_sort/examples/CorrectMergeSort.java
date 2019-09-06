// 1)
if (hi - lo <= 0) {
    return;
}

int mid = (hi + lo) / 2;
sort(a, aux, lo, mid);
sort(a, aux, mid+1, hi);

merge(a, aux, lo, mid, hi);

// 2)
Comparable[] aux = new Comparable[a.length];
for (int i = 0; i < aux.length; i++) {
    aux[i] = a[i];
}

sort(a, aux, 0, a.length - 1);
