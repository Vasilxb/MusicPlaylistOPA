package auth.playlist

default authorized := false

authorized if {
    count(allow) > 0
}

# Anyone logged in can open create form
allow["playlist_create_form"] if {
    input.method == "GET"
    input.uri == "/music-playlists/create"
    is_authenticated
}

# Anyone logged in can create
allow["playlist_create"] if {
    input.method == "POST"
    input.uri == "/music-playlists/save"
    is_authenticated
}

# Anyone logged in can open edit form
allow["playlist_edit"] if {
    input.method == "GET"
    regex.match("^/music-playlists/[^/]+/edit$", input.uri)
    is_authenticated
}

# Anyone logged in can update
allow["playlist_update"] if {
    input.method == "POST"
    regex.match("^/music-playlists/[^/]+/update$", input.uri)
    is_authenticated
}

# Anyone logged in can delete
allow["playlist_delete"] if {
    input.method == "POST"
    regex.match("^/music-playlists/[^/]+/delete$", input.uri)
    is_authenticated
}

# Admin is always authorized
allow["admin"] if {
    has_authority("ROLE_ADMIN")
}

is_authenticated if {
    input.principal != "anonymousUser"
}

has_authority(required) if {
    some i
    input.authorities[i] == required
}
